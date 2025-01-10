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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        System.out.println("Received username: " + username);  // 打印接收到的用户名
        System.out.println("Received password: " + password);  // 打印接收到的密码

        try {
            // 手动进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 打印认证信息
            System.out.println("Authentication successful: " + authentication);

            // 登录成功后生成 JWT
            String token = JwtUtil.generateToken(username);  // 自定义方法生成 JWT

            // 将 JWT 设置到响应头中
            response.setHeader("Authorization", "Bearer " + token);

            return Result.success("Login successful");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(401, "Invalid credentials");
        }
    }

    // 用户注册
    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        Users user = new Users();
        user.setUsername(requestBody.get("username"));
        String encodedPassword = passwordEncoder.encode(requestBody.get("password"));
        user.setPassword(encodedPassword);
        user.setEmail(requestBody.get("email"));

        boolean isRegistered = userService.register(user);
        if (isRegistered) {
            return Result.success("Registration successful!");
        } else {
            return Result.fail(400, "Registration failed! Username already exists.");
        }
    }

    // 密码找回
    @PostMapping("/forgot-password")
    public Result<String> forgotPassword(@RequestParam String username) {
        boolean result = userService.sendPasswordResetLink(username);
        if (result) {
            return Result.success("Password reset link sent!");
        } else {
            return Result.fail(404, "User not found!");
        }
    }

    // 更新用户信息
    @PutMapping("/update")
    public Result<String> updateUserInfo(@RequestBody Users user) {
        boolean result = userService.updateUserInfo(user);
        if (result) {
            return Result.success("User information updated successfully!");
        } else {
            return Result.fail(500, "Failed to update user information!");
        }
    }
}