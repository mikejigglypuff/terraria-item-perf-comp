package com.terraria_item_perf_comp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class AuthTokenInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (Objects.isNull(authorization) || !authorization.startsWith(BEARER_PREFIX)) {
            return true;
        }

        String token = authorization.substring(BEARER_PREFIX.length());
        request.setAttribute("token", token);

        return true;
    }
}

