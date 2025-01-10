package com.animalSecurity.controller;

import com.animalSecurity.entity.Pets;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IPetsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 用户：根据用户ID获取宠物列表
    @GetMapping("/list")
    public Result<List<Pets>> getPetsByUserId(@RequestParam String userId) {
        List<Pets> pets = petService.getPetsByUserId(userId);
        if (pets != null && !pets.isEmpty()) {
            return Result.success(pets);
        }
        return Result.fail(404, "No pets found for the user.");
    }

    // 添加新宠物
    @PostMapping
    public Result<String> addPet(@RequestBody Pets pet) {
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