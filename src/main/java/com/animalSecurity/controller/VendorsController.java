package com.animalSecurity.controller;

import com.animalSecurity.entity.Vendors;
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
@Controller
@RequestMapping("/vendors")
public class VendorsController {
    @Autowired
    private IVendorsService vendorService;

    // 商家注册
    @PostMapping("/register")
    public String register(@RequestBody Vendors vendor) {
        if (vendorService.registerVendor(vendor)) {
            return "Registration successful!";
        }
        return "Vendor name already exists!";
    }

    // 商家登录
    @PostMapping("/login")
    public String login(@RequestParam String vendorName, @RequestParam String password) {
        String token = vendorService.loginVendor(vendorName, password);
        if (token != null) {
            return "Login successful! Token: " + token;
        }
        return "Invalid vendor name or password.";
    }

    // 查看商家信息
    @GetMapping("/info")
    public Vendors getVendorInfo(Authentication authentication) {
        Integer vendorId = Integer.parseInt(authentication.getName());
        return vendorService.getVendorById(vendorId);
    }

    // 更新商家信息
    @PutMapping("/info")
    public String updateVendorInfo(@RequestBody Vendors vendor, Authentication authentication) {
        Integer vendorId = Integer.parseInt(authentication.getName());
        vendor.setVendorId(vendorId);
        if (vendorService.updateVendor(vendor)) {
            return "Information updated successfully!";
        }
        return "Failed to update information.";
    }


}
