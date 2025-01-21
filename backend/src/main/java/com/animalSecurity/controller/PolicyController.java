package com.animalSecurity.controller;

import com.animalSecurity.entity.Policy;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Result<List<Policy>> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        if (policies != null && !policies.isEmpty()) {
            return Result.success(policies);
        }
        return Result.fail(404, "No policies found.");
    }

    // 用户：查看保险产品详情
    @GetMapping("/{id}")
    public Result<Policy> getPolicyById(@PathVariable int id) {
        Policy policy = policyService.getPolicyById(id);
        if (policy != null) {
            return Result.success(policy);
        }
        return Result.fail(404, "Policy not found.");
    }

    // 管理员：添加保险产品
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Result<String> addPolicy(@RequestBody Policy policy) {
        boolean success = policyService.addPolicy(policy);
        if (success) {
            return Result.success("Policy added successfully!");
        }
        return Result.fail(500, "Failed to add policy.");
    }

    // 管理员：更新保险产品信息
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Result<String> updatePolicy(@PathVariable int id, @RequestBody Policy policy) {
        policy.setPolicyId(id);
        boolean success = policyService.updatePolicy(policy);
        if (success) {
            return Result.success("Policy updated successfully!");
        }
        return Result.fail(500, "Failed to update policy.");
    }

    // 管理员：删除保险产品
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Result<String> deletePolicy(@PathVariable int id) {
        boolean success = policyService.deletePolicy(id);
        if (success) {
            return Result.success("Policy deleted successfully!");
        }
        return Result.fail(500, "Failed to delete policy.");
    }
}