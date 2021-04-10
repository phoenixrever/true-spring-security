package com.phoenixhell.securityUaa.security.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.phoenixhell.securityUaa.entity.TPermission;
import com.phoenixhell.securityUaa.mapper.TPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/4/10 0010-上午 11:20
 */
//@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Resource
    private TPermissionMapper permissionMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
//        客户端会验证一次 密码会验证一次 一共2次
        Object principal = authenticationSuccessEvent.getAuthentication().getPrincipal();
        if(!(principal instanceof User)) {
            User user = (User) principal;
            com.phoenixhell.securityBase.entity.User userEntity = JSON.parseObject(user.getUsername(), new TypeReference<com.phoenixhell.securityBase.entity.User>() {
            });
            List<TPermission> list = permissionMapper.getPermissionsListByUserId(String.valueOf(userEntity.getId()));
            List<String> permissionList = new ArrayList<>();
            list.forEach(p -> {
                permissionList.add(p.getCode());
            });
            redisTemplate.opsForValue().set(userEntity.getUsername(), permissionList);
        }
    }
}
