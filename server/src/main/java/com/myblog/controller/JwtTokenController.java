package com.myblog.controller;

import com.myblog.constant.JwtClaimsConstant;
import com.myblog.properties.JwtProperties;
import com.myblog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenController {

    @Autowired
    private JwtProperties jwtProperties;

    public Long getUserId(String token) {

        // 解析JWT令牌
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
        // 从Claims对象中获取用户ID
        return Long.valueOf(claims.get(JwtClaimsConstant.ADMIN_ID).toString());
    }
}
