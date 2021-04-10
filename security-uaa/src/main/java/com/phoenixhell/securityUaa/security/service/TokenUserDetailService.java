package com.phoenixhell.securityUaa.security.service;

import com.alibaba.fastjson.JSON;
import com.phoenixhell.securityUaa.entity.TPermission;
import com.phoenixhell.securityUaa.entity.TUser;
import com.phoenixhell.securityUaa.mapper.TPermissionMapper;
import com.phoenixhell.securityUaa.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/4/9 0009-上午 10:53
 */
@Service("userDetailService")
public class TokenUserDetailService implements UserDetailsService {
    @Autowired
    private TUserService userService;
    @Resource
    private TPermissionMapper permissionMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //传入字符串形成collection  实际中 数据库中直接查 生成collection
//        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("p1");
//        return new User("shadow", new BCryptPasswordEncoder().encode("123"),grantedAuthorities);
        TUser user = userService.query().eq("username",username).one();
        if(user==null){
            return  null;
        }
        List<TPermission> list = permissionMapper.getPermissionsListByUserId(String.valueOf(user.getId()));
        List<String> permissionList = new ArrayList<>();
        list.forEach(p -> {
            permissionList.add(p.getCode());
        });
        redisTemplate.opsForValue().set(user.getUsername(), permissionList);
        //权限放这里太长了先放个空 先存redis里面 以后去redis里去取
        List<GrantedAuthority> authorityList =new ArrayList<>();
//        list.forEach(p->{
//            authorityList.add(new SimpleGrantedAuthority(p.getCode()));
//        });
        //创建userDetails
        //这里将user转为json，将整体user存入userDetails
        String principal = JSON.toJSONString(user);
        return new User(principal, user.getPassword(), authorityList);
    }
}
