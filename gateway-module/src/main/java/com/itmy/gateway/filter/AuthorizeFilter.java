package com.itmy.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.itmy.constant.Consts;
import com.itmy.constant.Rediskey;
import com.itmy.entity.CurrentUser;
import com.itmy.entity.base.Response;
import com.itmy.enums.ErrorEnum;
import com.itmy.utils.JwtUtils;
import com.itmy.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Objects;

/**
 * @Author: niusaibo
 * @date: 2023-10-12 10:09
 *
 * 登录拦截器 获取header中 的token信息校验拦截
 */
@Component
public class AuthorizeFilter implements GlobalFilter , Ordered {

    private static final String AUTHORIZE_TOKEN = "Authorization";

    private static AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 例外路径(按顺序)
     * 1、接口列表
     *
     */
    private static final String[] EXCLUDE_PATHS = new String[] {
            "/user/account/login",
            "/user/account/login/user/captcha"
    };

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 不需要拦截的url直接放行
        if(needLogin(request.getPath().toString())){
            return chain.filter(exchange);
        }
        String accessToken = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            //return loginResponse(exchange);
            return setNoAuth(exchange, Response.fail(ErrorEnum.UUC_USER_NOT_LOGIN));
        }
        CurrentUser currentUser = null;
        try {
            currentUser = JwtUtils.getCurrentUser(accessToken);
        }
        catch (Exception e) {
            setNoAuth(exchange, Response.fail(ErrorEnum.UUC_TOKEN_INVALID));
        }
        if (Objects.isNull(currentUser) || Objects.isNull(currentUser.getEmail())) {
           return setNoAuth(exchange, Response.fail(ErrorEnum.UUC_TOKEN_INVALID));
        }
        //获取Redis Token 判断token 是否为最新
        RBucket<Long> bucket = redissonClient.getBucket(Rediskey.ACCOUNT_LOGIN_TOKEN
                + currentUser.getLoginDeviceType() + Consts.POINT + currentUser.getId(), LongCodec.INSTANCE);
        if (Objects.isNull(bucket.get())) {
            return setNoAuth(exchange, Response.fail(ErrorEnum.UUC_TOKEN_INVALID));
        }
       // UserHolder.setCurrentUser(currentUser);
       // 将accessToken中的信息传入到请求头中，方便后续接口使用
       // request.mutate().header("LOGIN_USER", JSONObject.toJSONString(currentUser)).build();
       // request.mutate().header("Authorization", accessToken).build();
       // System.out.println("1111111111111111111");
       // return null;
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }

    public static Mono<Void> setNoAuth(ServerWebExchange exchange,Response response) {
        ServerHttpResponse res = exchange.getResponse();
        byte[] bytes = JSONObject.toJSONBytes(response);
        res.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        DataBuffer buffer = res.bufferFactory().wrap(bytes);
        return res.writeWith(Flux.just(buffer));
    }


    public static boolean needLogin(String uri){
        for (String pattern : EXCLUDE_PATHS) {
            if (matcher.match(pattern, uri)) {
                // 不需要拦截
                return true;
            }
        }
        return false;
    }

}
