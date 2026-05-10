package com.example.ambulancedispatch.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String path = request.getRequestURI();

        if (isPublicPath(path) || request.getSession(false) != null
                && request.getSession(false).getAttribute("loggedInUser") != null) {
            return true;
        }

        if (isApiPath(path)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Please log in first.\"}");
            return false;
        }

        response.sendRedirect("/login");
        return false;
    }

    private boolean isPublicPath(String path) {
        return path.equals("/login")
                || path.equals("/signup")
                || path.equals("/error")
                || path.equals("/favicon.ico")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/webjars/");
    }

    private boolean isApiPath(String path) {
        return path.startsWith("/api")
                || path.startsWith("/ambulance")
                || path.startsWith("/dispatch")
                || path.startsWith("/emergency")
                || path.startsWith("/hospital");
    }
}
