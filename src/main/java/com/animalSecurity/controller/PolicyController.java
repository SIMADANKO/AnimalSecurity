package com.animalSecurity.controller;

import com.animalSecurity.entity.Policy;
import com.animalSecurity.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 保险产品表 前端控制器
 * </p>
 *
 * @author lu
 * @since 2024-12-26
 */
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private IPolicyService policyService;

    // 用户：查看所有保险产品
    @GetMapping
    public List<Policy> getAllPolicies() {
        return policyService.getAllPolicies();
    }

    // 用户：查看保险产品详情
    @GetMapping("/{id}")
    public Policy getPolicyById(@PathVariable int id) {
        return policyService.getPolicyById(id);
    }

    // 管理员：添加保险产品
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String addPolicy(@RequestBody Policy policy) {
        if (policyService.addPolicy(policy)) {
            return "Policy added successfully!";
        }
        return "Failed to add policy.";
    }

    // 管理员：更新保险产品信息
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public String updatePolicy(@PathVariable int id, @RequestBody Policy policy) {
        policy.setPolicyId(id);
        if (policyService.updatePolicy(policy)) {
            return "Policy updated successfully!";
        }
        return "Failed to update policy.";
    }

    // 管理员：删除保险产品
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deletePolicy(@PathVariable int id) {
        if (policyService.deletePolicy(id)) {
            return "Policy deleted successfully!";
        }
        return "Failed to delete policy.";
    }
}