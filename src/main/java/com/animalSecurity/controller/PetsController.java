package com.animalSecurity.controller;

import com.animalSecurity.config.CustomUserDetails;
import com.animalSecurity.entity.Pets;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IPetsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 宠物表 前端控制器
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@RestController
@RequestMapping("/pets")
public class PetsController {
    @Autowired
    private IPetsService petService;

    // 用户：根据用户ID获取宠物分页列表
    @GetMapping("/list")
    public Result<Page<Pets>> getPetsByUserId(
            @RequestParam Integer page,
            @RequestParam Integer size,
            Authentication authentication) {

        // 获取 CustomUserDetails
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 从 Token (Authentication) 中获取当前用户的 ID
        Integer userId = userDetails.getUserId();

        System.out.println(authentication);

        // 创建分页对象
        Page<Pets> pageParam = new Page<>(page, size);

        // 调用 service 层方法获取分页数据
        Page<Pets> petsPage = petService.getPetsByUserId(userId, pageParam);

        // 判断是否有数据并返回结果
        if (petsPage != null && !petsPage.getRecords().isEmpty()) {
            return Result.success(petsPage);
        }
        return Result.fail(404, "No pets found for the user.");
    }

    // 添加新宠物
    @PostMapping
    public Result<String> addPet(@RequestBody Pets pet, Authentication authentication) {
        // 获取 CustomUserDetails
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 从 Token (Authentication) 中获取当前用户的 ID
        Integer userId = userDetails.getUserId();

        // 将 userId 设置到 pet 对象中
        pet.setUserId(userId);

        // 调用 service 层方法添加宠物
        boolean success = petService.addPet(pet);
        if (success) {
            return Result.success("Pet added successfully!");
        }
        return Result.fail(500, "Failed to add pet.");
    }

    // 更新宠物信息
    @PutMapping
    public Result<String> updatePet(@RequestBody Pets pet) {
        boolean success = petService.updatePet(pet);
        if (success) {
            return Result.success("Pet updated successfully!");
        }
        return Result.fail(500, "Failed to update pet.");
    }

    // 删除宠物
    @DeleteMapping("/{id}")
    public Result<String> deletePet(@PathVariable int id) {
        boolean success = petService.deletePet(id);
        if (success) {
            return Result.success("Pet deleted successfully!");
        }
        return Result.fail(500, "Failed to delete pet.");
    }
}