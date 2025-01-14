package com.animalSecurity.service;
import com.animalSecurity.entity.Policy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 保险产品表 Mapper 接口
 * </p>
 *
 * @author lu
 * @since 2024-12-26
 */
public interface IPolicyService extends IService<Policy> {
    List<Policy> getAllPolicies();              // 查询所有保险产品
    Policy getPolicyById(int id);               // 根据 ID 查询保险产品详情
    boolean addPolicy(Policy policy);           // 添加保险产品
    boolean updatePolicy(Policy policy);        // 更新保险产品信息
    boolean deletePolicy(int id);               // 删除保险产品
}
