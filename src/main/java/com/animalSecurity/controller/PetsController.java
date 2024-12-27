package com.animalSecurity.controller;

import com.animalSecurity.entity.Pets;
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
@Controller
@RequestMapping("/pets")
public class PetsController {
    @Autowired
    private IPetsService petService;

    @GetMapping
    List<Pets> getPetsByUserId(String userId) {
        return petService.getPetsByUserId(userId);
    }

    @PostMapping
    public String addPet(@RequestBody Pets pet) {
        if (petService.addPet(pet)) {
            return "Pet added successfully!";
        }
        return "Failed to add pet.";
    }

    @PutMapping
    public String updatePet(@RequestBody Pets pet) {
        if (petService.updatePet(pet)) {
            return "Pet updated successfully!";
        }
        return "Failed to update pet.";
    }

    @DeleteMapping("/{id}")
    public String deletePet(@PathVariable int id) {
        if (petService.deletePet(id)) {
            return "Pet deleted successfully!";
        }
        return "Failed to delete pet.";
    }

}
