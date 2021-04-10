package com.phoenixhell.order.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import com.phoenixhell.securityBase.entity.User;
import com.phoenixhell.securityBase.utils.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenStore tokenStore;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("json-token");
        /*
        if (StringUtils.isNotBlank(token)){
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            JSONObject jsonObject = JSON.parseObject(json);
            //获取用户身份信息
            String principal = jsonObject.getString("principal");
            User user = JSON.parseObject(principal, User.class);
//            JSONArray tempJsonArray = jsonObject.getJSONArray("authorities");
//            String[] authorities =  tempJsonArray.toArray(new String[0]);
           List<String> list= (List<String>) redisTemplate.opsForValue().get(user.getUsername());
            String[] authorities = list.toArray(new String[list.size()]);
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(authorities);
            //身份信息、权限信息填充到用户身份token对象中
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,authorityList);
            System.out.println(authenticationToken);
            //创建details
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //将authenticationToken填充到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
         */
//        String bearerToken =request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = request.getHeader("token");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
        //取出用户身份信息
        String principal = (String)additionalInformation.get("user_name");
        User user = JSON.parseObject(principal, User.class);
        List<String> list= (List<String>) redisTemplate.opsForValue().get(user.getUsername());
        String[] authorities = list.toArray(new String[list.size()]);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(authorities);
        //身份信息、权限信息填充到用户身份token对象中
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,authorityList);
        System.out.println(authenticationToken);
        //创建details
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //将authenticationToken填充到安全上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
