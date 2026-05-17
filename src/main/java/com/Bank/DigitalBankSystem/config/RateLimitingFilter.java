package com.Bank.DigitalBankSystem.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 5;
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        String path = servletRequest.getRequestURI();

        if (!path.startsWith(servletRequest.getContextPath() + "/transaction")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String clientIp = servletRequest.getRemoteAddr();
        requestCounts.putIfAbsent(clientIp, new AtomicInteger(0));
        int currentCount = requestCounts.get(clientIp).incrementAndGet();
        System.out.println(requestCounts.get(clientIp).get());

        if (currentCount > MAX_REQUESTS_PER_MINUTE) {
            servletResponse.setStatus(429); //HttpStatus -> TOO_MANY_REQUESTS
            servletResponse.getWriter().write("Too many requests. Please try again later.");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Scheduled(fixedRate = 60000)
    public void resetCounter() { //Resets the Bucket every minute
        this.requestCounts.clear();
        System.out.println("Counter reset");
    }
}
