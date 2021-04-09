package com.phoenixhell.securityUaa.config;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-上午 11:00
 */
@Configuration
public class TokenConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
//    @Bean
//    public TokenStore tokenStore(){
//        return new InMemoryTokenStore();
//    }

    private static final String SIGNING_KEY="secret";

    //redisTokenStore
    @Bean
    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
