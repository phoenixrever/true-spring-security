package com.phoenixhell.securityUaa.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/4/9 0009-上午 10:53
 */
@Service("userDetailService")
public class TokenUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //传入字符串形成collection  实际中 数据库中直接查 生成collection
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("p1");
        return new User("shadow", new BCryptPasswordEncoder().encode("123"),grantedAuthorities);
    }
}
