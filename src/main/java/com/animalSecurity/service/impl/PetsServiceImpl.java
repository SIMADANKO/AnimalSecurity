package com.animalSecurity.service.impl;

import com.animalSecurity.entity.Pets;
import com.animalSecurity.mapper.PetsMapper;
import com.animalSecurity.service.IPetsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宠物表 服务实现类
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@Service
public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pets> implements IPetsService {

}
