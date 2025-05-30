package com.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行自定义登录接口（替换为实际路径）
                .antMatchers("/**").permitAll()
                // 其他路径按需配置（例如：所有路径需认证）
                .anyRequest().authenticated()
                .and()
                // 关闭 CSRF（如果是 RESTful 接口，可能需要关闭）
                .csrf().disable();
    }
}