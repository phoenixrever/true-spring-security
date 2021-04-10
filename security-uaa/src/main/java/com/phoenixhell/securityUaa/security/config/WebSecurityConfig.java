package com.phoenixhell.securityUaa.security.config;

import com.phoenixhell.securityUaa.security.handler.FailureHandler;
import com.phoenixhell.securityUaa.security.handler.LogoutHandler;
import com.phoenixhell.securityUaa.security.handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-下午 1:19
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Resource
//    private UserDetailsService userDetailsService;

    @Autowired
    private SuccessHandler successHandler;

    @Autowired
    private FailureHandler failureHandler;

    @Resource
    private LogoutHandler logoutHandler;

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

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher
            (ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        List<GrantedAuthority> authority=new ArrayList<>();
//        authority.add(new SimpleGrantedAuthority("p1"));
//        auth.inMemoryAuthentication().withUser(new User("shadow", new BCryptPasswordEncoder().encode("123"),authority));
//        auth.authenticationProvider(authenticationProvider());
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(bCryptPasswordEncoder());
//        provider.setHideUserNotFoundExceptions(false);
//        return provider;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin()
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler).permitAll()
                .and()
                .logout().logoutSuccessHandler(logoutHandler)
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
//                );
//    }
}
