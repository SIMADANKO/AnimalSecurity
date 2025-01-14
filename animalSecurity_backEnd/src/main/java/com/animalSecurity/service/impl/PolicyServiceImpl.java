package com.animalSecurity.service.impl;
import com.animalSecurity.entity.Policy;
import com.animalSecurity.mapper.PolicyMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.animalSecurity.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 保险产品表 服务实现类
 * </p>
 *
 * @author lu
 * @since 2024-12-26
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements IPolicyService {
    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public List<Policy> getAllPolicies() {
        return policyMapper.selectList(new QueryWrapper<>()); // 查询所有记录
    }

    @Override
    public Policy getPolicyById(int id) {
        return policyMapper.selectById(id); // 根据 ID 查询记录
    }

    @Override
    public boolean addPolicy(Policy policy) {
        return policyMapper.insert(policy) > 0; // 插入成功返回 true
    }

    @Override
    public boolean updatePolicy(Policy policy) {
        return policyMapper.updateById(policy) > 0; // 更新成功返回 true
    }

    @Override
    public boolean deletePolicy(int id) {
        return policyMapper.deleteById(id) > 0; // 删除成功返回 true
    }
}
