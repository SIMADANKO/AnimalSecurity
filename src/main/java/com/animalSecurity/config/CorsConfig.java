package com.animalSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有路径都支持跨域
                .allowedOrigins("http://localhost:8080", "https://*.example.com", "https://*.anotherdomain.com") // 允许的来源
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .allowCredentials(true) // 允许发送凭证（如 cookies）
                .maxAge(3600) // 预检请求的最大缓存时间（单位：秒）
                .allowedHeaders("*") // 允许所有请求头
         .exposedHeaders("Authorization");

    }

    // 如果需要，暴露配置源给 Spring Security 使用
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080");  // 前端地址
        configuration.addAllowedMethod("*");  // 所有请求方法
        configuration.addAllowedHeader("*");  // 所有请求头
        configuration.setAllowCredentials(true);  // 允许发送 cookies

        // 显式允许 Authorization 头部暴露
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}