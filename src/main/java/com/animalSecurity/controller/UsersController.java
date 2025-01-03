package com.animalSecurity.controller;

import com.animalSecurity.entity.Users;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IUsersService;
import com.animalSecurity.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ResponseBody
    public Result<String> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        try {
            // 使用 Spring Security 进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 登录成功后生成 JWT
            String token = JwtUtil.generateToken(String.valueOf(authentication));

            // 将 JWT 设置到响应头中
            response.setHeader("Authorization", "Bearer " + token);

            // 返回统一响应
            return Result.success("Login successful");
        } catch (Exception e) {
            // 捕获异常并返回失败消息
            return Result.fail("Invalid credentials");
        }
    }


    // 用户注册
    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody Users user) {
        boolean isRegistered = userService.register(user);
        if (isRegistered) {
            return "Registration successful!";
        } else {
            return "Registration failed! Username already exists.";
        }
    }

    // 密码找回
    @PostMapping("/forgot-password")
    @ResponseBody
    public String forgotPassword(@RequestParam String username) {
        boolean result = userService.sendPasswordResetLink(username);
        if (result) {
            return "Password reset link sent!";
        } else {
            return "User not found!";
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public String updateUserInfo(@RequestBody Users user) {
        boolean result = userService.updateUserInfo(user);
        if (result) {
            return "User information updated successfully!";
        } else {
            return "Failed to update user information!";
        }
    }
    }