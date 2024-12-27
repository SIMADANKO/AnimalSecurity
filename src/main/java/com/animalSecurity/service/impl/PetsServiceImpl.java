package com.animalSecurity.service.impl;

import com.animalSecurity.entity.Pets;
import com.animalSecurity.mapper.PetsMapper;
import com.animalSecurity.service.IPetsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private PetsMapper petMapper;

    //查询当前用户所有宠物
    @Override
    public List<Pets> getPetsByUserId(String userId) {
        QueryWrapper<Pets> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id", userId); // 假设表中有 owner_id 字段表示用户 ID
        return petMapper.selectList(queryWrapper);
    }

    // 添加宠物
    @Override
    public boolean addPet(Pets pet) {
        return petMapper.insert(pet) > 0; // 插入成功返回 true
    }

    // 更新宠物信息
    @Override
    public boolean updatePet(Pets pet) {
        return petMapper.updateById(pet) > 0; // 更新成功返回 true
    }

    // 删除宠物
    @Override
    public boolean deletePet(int id) {
        return petMapper.deleteById(id) > 0; // 删除成功返回 true
    }
}
