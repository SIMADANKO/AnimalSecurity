package com.animalSecurity.service.impl;
import com.animalSecurity.entity.Policy;
import com.animalSecurity.mapper.PolicyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.animalSecurity.service.IPolicyService;
import org.springframework.stereotype.Service;

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

}
