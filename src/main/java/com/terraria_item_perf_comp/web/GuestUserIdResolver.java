package com.terraria_item_perf_comp.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.terraria_item_perf_comp.exception.BalanceGameException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class GuestUserIdResolver {

    public static final String GUEST_SESSION_COOKIE_NAME = "guest_session";

    public int resolveRequiredUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new BalanceGameException(HttpStatus.UNAUTHORIZED, "Guest session cookie is required");
        }
        for (Cookie cookie : cookies) {
            if (!GUEST_SESSION_COOKIE_NAME.equals(cookie.getName())) {
                continue;
            }
            String value = cookie.getValue();
            if (value == null || value.isEmpty()) {
                throw new BalanceGameException(HttpStatus.UNAUTHORIZED, "Guest session cookie is empty");
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                throw new BalanceGameException(HttpStatus.BAD_REQUEST, "Invalid guest session cookie value");
            }
        }
        throw new BalanceGameException(HttpStatus.UNAUTHORIZED, "Guest session cookie is required");
    }
}
