package com.blog.interfaces.config;

import com.blog.common.constant.CommonConstants;
import com.blog.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(CommonConstants.AUTHORIZATION_HEADER);
        
        if (authHeader != null && authHeader.startsWith(CommonConstants.BEARER_PREFIX)) {
            String token = authHeader.substring(CommonConstants.BEARER_PREFIX.length());
            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.getUserIdFromToken(token);
                String username = jwtUtil.getUsernameFromToken(token);
                String role = jwtUtil.getRoleFromToken(token);
                
                request.setAttribute(CommonConstants.USER_ID_KEY, userId);
                request.setAttribute(CommonConstants.USERNAME_KEY, username);
                request.setAttribute(CommonConstants.ROLE_KEY, role);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
