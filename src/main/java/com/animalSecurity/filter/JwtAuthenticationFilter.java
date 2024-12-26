package com.animalSecurity.filter;

import com.animalSecurity.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取请求头中的 Authorization 信息
        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        // 检查是否包含 Bearer Token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // 去掉 "Bearer " 前缀
            try {
                // 从 Token 中解析出用户名
                Claims claims = JwtUtil.extractClaims(token);
                username = claims.getSubject();
            } catch (Exception e) {
                System.out.println("无效的 JWT: " + e.getMessage());
            }
        }

        // 验证 Token 并设置 Spring Security 上下文
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 验证 Token 是否有效
            if (JwtUtil.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // 设置认证信息到上下文
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}
