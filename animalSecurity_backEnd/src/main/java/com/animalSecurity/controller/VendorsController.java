package com.animalSecurity.controller;

import com.animalSecurity.entity.Vendors;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IVendorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


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