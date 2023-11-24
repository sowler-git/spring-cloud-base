package com.itmy.utils;



import com.itmy.entity.CurrentUser;
import com.itmy.entity.User;
import com.itmy.enums.LoginDeviceTypeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;
import java.util.Objects;

/**
 * @author nsbo
 * @Date 2023/10/12 10:36
 */
public class JwtUtils {

    public static final String SUBJECT = "wordorder";

    /**
     * 过期时间，毫秒，一天
     */
    public static final long PC_EXPIRE = 1000L * 60 * 60;

    /**
     * PAD 一个月
     */
    public static final long PAD_EXPIRE = 1000L * 60 * 60 * 24 * 30;

    /**
     * 秘钥
     */
    public static final  String APPSECRET = "32EA98FBB12C4C5569CC2511CB4247BD/CWlhbnl1d29ya29yZGVyYWRtaW5pc3RyYXRpb25hbmRtYW5hZ2VtZW50";

    /**
     * PC jwt生成token
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user){
        String token = geneJsonToken(user, LoginDeviceTypeEnum.PC.getCode(),System.currentTimeMillis() + PC_EXPIRE);
        return token;
    }

    public static String geneJsonDapToken(User user){
        String token = geneJsonToken(user, LoginDeviceTypeEnum.PAD.getCode(), System.currentTimeMillis() + PAD_EXPIRE);
        return token;
    }

    public static String geneJsonToken(User user, Integer loginDeviceType,Long expiration){
        if(user == null || user.getId() == null || user.getAccount() == null
                || user.getPassword()==null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("userId",user.getId())
                .claim("userName",user.getUserName())
                .claim("account",user.getAccount())
                .claim("email",user.getEmail())
                .claim("phoneNumber",user.getPhoneNumber())
                .claim("loginDeviceType", loginDeviceType)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token ){
        try{
            final Claims claims =  Jwts.parser().setSigningKey(APPSECRET).
                    parseClaimsJws(token).getBody();
            return  claims;
        }catch (Exception e){ }
        return null;

    }

    /**
     * 获取当前用户信息
     * @param token
     * @return
     */
    public static CurrentUser getCurrentUser(String token) {
        Claims claims = checkJWT(token);
        CurrentUser user=new CurrentUser();
        if (!Objects.isNull(claims.get("userId"))){
            user.setId((Long) claims.get("userId"));
        }
        if (!Objects.isNull(claims.get("account"))){
            user.setAccount((String) claims.get("account"));
        }
        if (!Objects.isNull(claims.get("userName"))){
            user.setUserName((String) claims.get("userName"));
        }
        if (!Objects.isNull(claims.get("phoneNumber"))){
            user.setPhoneNumber((String) claims.get("phoneNumber"));
        }
        if (!Objects.isNull(claims.get("email"))){
            user.setEmail((String) claims.get("email"));
        }
        if (!Objects.isNull(claims.get("loginDeviceType"))){
            user.setLoginDeviceType((Integer) claims.get("loginDeviceType"));
        }
        return user;
    }


}
