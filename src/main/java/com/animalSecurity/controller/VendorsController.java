package com.animalSecurity.controller;

import com.animalSecurity.entity.Vendors;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IVendorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 商家表 前端控制器
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@RestController
@RequestMapping("/vendors")
public class VendorsController {

    @Autowired
    private IVendorsService vendorService;

    // 商家注册
    @PostMapping("/register")
    public Result<String> register(@RequestBody Vendors vendor) {
        if (vendorService.registerVendor(vendor)) {
            return Result.success("Registration successful!");
        }
        return Result.fail(400, "Vendor name already exists!");
    }

     //商家登录
    @PostMapping("/login")
    public Result<String> login(@RequestParam String vendorName, @RequestParam String password) {
        String token = vendorService.loginVendor(vendorName, password);
        if (token != null) {
            return Result.success("Login successful! Token: " + token);
        }
        return Result.fail(401, "Invalid vendor name or password.");
    }

    // 查看商家信息
    @GetMapping("/info")
    public Result<Vendors> getVendorInfo(Authentication authentication) {
        Integer vendorId = Integer.parseInt(authentication.getName());
        Vendors vendor = vendorService.getVendorById(vendorId);
        if (vendor != null) {
            return Result.success(vendor);
        }
        return Result.fail(404, "Vendor not found.");
    }

    // 更新商家信息
    @PutMapping("/info")
    public Result<String> updateVendorInfo(@RequestBody Vendors vendor, Authentication authentication) {
        Integer vendorId = Integer.parseInt(authentication.getName());
        vendor.setVendorId(vendorId);
        if (vendorService.updateVendor(vendor)) {
            return Result.success("Information updated successfully!");
        }
        return Result.fail(500, "Failed to update information.");
    }
}