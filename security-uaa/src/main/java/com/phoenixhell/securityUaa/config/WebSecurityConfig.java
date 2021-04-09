package com.phoenixhell.securityUaa.config;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-下午 1:19
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    //不配置会爆authenticationManager未定义错误 貌似版本问题
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        List<GrantedAuthority> authority=new ArrayList<>();
//        authority.add(new SimpleGrantedAuthority("p1"));
//        auth.inMemoryAuthentication().withUser(new User("shadow", new BCryptPasswordEncoder().encode("123"),authority));
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .authorizeRequests()
//                .antMatchers("/r/r1").hasAnyAuthority("p1")
                .antMatchers("/oauth*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
                );
    }
}
