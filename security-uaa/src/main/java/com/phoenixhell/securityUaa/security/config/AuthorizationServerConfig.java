package com.phoenixhell.securityUaa.security.config;

import com.phoenixhell.securityUaa.security.exception.WebResponseTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-上午 10:29
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;
//    @Autowired
//    private AuthorizationCodeServices authorizationCodeServices;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 客户端详情
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
        clients.inMemory() // 使用in‐memory存储
                .withClient("c1")// client_id 客户端id
                .secret(new BCryptPasswordEncoder().encode("secret")) //客户端密钥
                .resourceIds("rest1")//客户端可以访问的资源列表
                // 该client允许的授权类型 authorization_code, password, refresh_token, implicit, client_credentials
                .authorizedGrantTypes("authorization_code",
                        "password", "client_credentials", "implicit", "refresh_token")
                .scopes("all")//read 等等 允许的授权范围 all不是所有也是一种标识
                .autoApprove(false)//跳转到授权页面  true 不用跳转直接发令牌
                .redirectUris("http://www.baidu.com");//加上验证回调地址
        */
        //换到数据库存储用户信息
        clients.withClientDetails(clientDetailsService());
    }


    @Bean
    public ClientDetailsService clientDetailsService(){
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * 令牌管理服务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService());//客户端信息服务
        tokenServices.setSupportRefreshToken(true);//是否产生刷新令牌
        tokenServices.setTokenStore(tokenStore);//令牌存储策略
        //令牌增加
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        tokenServices.setAccessTokenValiditySeconds(60*60*2);//令牌默认有效期2小时
        tokenServices.setRefreshTokenValiditySeconds(60*60*24*3);//刷新令牌默认有效期3天
        return tokenServices;
    }

    /**
     * 令牌访问端点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//密码模式
                .authorizationCodeServices(authorizationCodeServices(dataSource))//授权码模式
                .tokenServices(tokenServices())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)//允许post提交访问令牌
                .exceptionTranslator(new WebResponseTranslator());

    }

    //设置授权码模式的授权码如何存取，暂时采用内存方式
//    @Bean 换成采用数据库
//    public AuthorizationCodeServices authorizationCodeServices(){
//        return new InMemoryAuthorizationCodeServices();
//    }

    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        return new JdbcAuthorizationCodeServices(dataSource);
    }



    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //tokenkey这个endpoint当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，
                // 使用授权服务的 /oauth/check_token 端点你需要在授权服务将这个端点暴露出去，
                //以便资源服务可以进行访问
                // 这里指这个endpoint完全公开。
                .tokenKeyAccess("permitAll()")
                //checkToken这个endpoint完全公开
                .checkTokenAccess("permitAll()")
                //允许表单认证
                .allowFormAuthenticationForClients();
    }
}