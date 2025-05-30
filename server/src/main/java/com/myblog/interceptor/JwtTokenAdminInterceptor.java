package com.myblog.interceptor;


import com.myblog.config.RequiredRoles;
import com.myblog.constant.JwtClaimsConstant;
import com.myblog.context.BaseContext;
import com.myblog.properties.JwtProperties;
import com.myblog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class  JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        RequiredRoles requiredRolesAnnotation = method.getAnnotation(RequiredRoles.class);

        if (requiredRolesAnnotation != null) {
            String[] requiredRoles = requiredRolesAnnotation.value();

            //1、从请求头中获取令牌
            String token = request.getHeader(jwtProperties.getAdminTokenName());

            request.setAttribute("Authorization", "Bearer " + token);
            //2、校验令牌
            try {
                log.info("jwt校验:{}", token);
                Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
                Long empId = Long.valueOf(claims.get(JwtClaimsConstant.ADMIN_ID).toString());
                log.info("当前用户id：{}", empId);

                BaseContext.setCurrentId(empId);
                String userRoles = claims.get("roles", String.class);
                boolean hasPermission = false;
                for (String role : requiredRoles) {
                    if (userRoles.contains(role)) {
                        hasPermission = true;
                        break;
                    }
                }

                if (userRoles.equals("admin")) {
                   hasPermission  = true;
                }

                if (!hasPermission) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }

            } catch (Exception ex) {
                //4、不通过，响应401状态码
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }
}
