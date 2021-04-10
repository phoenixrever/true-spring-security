package com.phoenixhell.order.security.config;

import com.phoenixhell.order.security.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-下午 3:35
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    public static final String RESOURCE_ID="res2";
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)//资源ID
                .tokenStore(tokenStore)// 本地验证jwt令牌
//                .tokenServices(tokenService())//远程验证令牌的服务
                //无状态 不保留登录信息每次登录都要带token
                .stateless(true)
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }


    /**
     * 远程服务解析令牌
     * @return
     */
//    @Bean 使用jwt后本地校验
    public ResourceServerTokenServices tokenService(){
        //使用远程服务请求授权服务器校验token,必须知道token的url,client_id,client_secret
        RemoteTokenServices services=new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:8001/oauth/check_token");
        services.setClientId("c1");
        services.setClientSecret("secret");
        return services;
    }
}
