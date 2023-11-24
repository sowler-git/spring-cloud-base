package com.itmy.config.mybatisplus;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.google.common.collect.Lists;
import com.itmy.entity.CurrentUser;
import com.itmy.utils.UserHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * @Author: niusaibo
 * @date: 2023-05-26 15:28
 */
@Configuration
public class MybatisPlusConfig {

    private static final String SYSTEM_TENANT_ID = "tenant_id";

    private static final List<String> IGNORE_TENANT_TABLES =
            Lists.newArrayList("tb_tenant",
                    "tb_timezone");

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        // SQL解析处理拦截：增加租户处理回调。
        TenantSqlParser tenantSqlParser = new TenantSqlParser().setTenantHandler(new TenantHandler() {

            @Override
            public Expression getTenantId() {
                // 从当前系统上下文中取出当前请求的服务商ID，通过解析器注入到SQL中。
                Long currentProviderId = UserHolder.getTenantId();
                if (null == currentProviderId) {
                    throw new RuntimeException("# getCurrentTenant error.");
                }
                return new LongValue(currentProviderId);
            }

            @Override
            public String getTenantIdColumn() {
                return SYSTEM_TENANT_ID;
            }

            @Override
            public boolean doTableFilter(String tableName) {
                CurrentUser currentUser = UserHolder.getCurrentUser();
                if (Objects.isNull(currentUser)) {
                    return true;
                }
                if (currentUser.getTenantId() != null && currentUser.getTenantId() == 0) {
                    return true;
                }
                // 忽略掉一些表：如租户表（tenant）本身不需要执行这样的处理。
                return IGNORE_TENANT_TABLES.stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
            }
        });
        paginationInterceptor.setSqlParserList(Lists.newArrayList(tenantSqlParser));
        return paginationInterceptor;
    }

//    /**
//     * 分页插件（官网最新）
//     */
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//    }

    @Bean
    public GlobalConfig globalConfig(@Qualifier("dbConfig") GlobalConfig.DbConfig dbConfig,
                                     @Qualifier("fillMetaObjectHandler") MetaObjectHandler metaObjectHandler,
                                     @Qualifier("sqlInjector") ISqlInjector sqlInjector) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setMetaObjectHandler(metaObjectHandler);
        globalConfig.setSqlInjector(sqlInjector);
        return globalConfig;
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new NoahSqlInjector();
    }

    @Bean
    public GlobalConfig.DbConfig dbConfig() {
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");
        return dbConfig;
    }


}
