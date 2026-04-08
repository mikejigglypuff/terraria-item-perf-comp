package com.terraria_item_perf_comp.config;

import com.terraria_item_perf_comp.models.User;
import com.terraria_item_perf_comp.service.GuestUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class GuestUserFilter extends OncePerRequestFilter {

    private static final String GUEST_SESSION_COOKIE_NAME = "guest_session";
    private static final String MAIN_PAGE_URL = "/";

    private final GuestUserService guestUserService;

    public GuestUserFilter(GuestUserService guestUserService) {
        this.guestUserService = guestUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 2. 쿠키 유무 확인
        Cookie sessionCookie = getCookie(request, GUEST_SESSION_COOKIE_NAME);

        if (sessionCookie != null && sessionCookie.getValue() != null && !sessionCookie.getValue().isEmpty()) {
            // 2-1. 쿠키가 있는 경우 상황 종료
            filterChain.doFilter(request, response);
            return;
        }

        // 2-2. 쿠키가 없는 경우
        // 3. 요청 IP 주소의 해시값이 DB 내 존재하는지 확인
        String clientIpAddress = getClientIpAddress(request);
        User guestUser = guestUserService.getOrCreateGuestUser(clientIpAddress);

        // 쿠키 생성
        Cookie cookie = new Cookie(GUEST_SESSION_COOKIE_NAME, String.valueOf(guestUser.getId()));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
        response.addCookie(cookie);

        // 리다이렉트 URL 반환
        response.sendRedirect(MAIN_PAGE_URL);
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}

