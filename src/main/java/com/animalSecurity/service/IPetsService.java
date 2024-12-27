package com.animalSecurity.service;

import com.animalSecurity.entity.Pets;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 宠物表 服务类
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
public interface IPetsService extends IService<Pets> {

    List<Pets> getPetsByUserId(String userId);// 查询所有宠物

    boolean addPet(Pets pet);              // 添加宠物

    boolean updatePet(Pets pet);           // 更新宠物信息

    boolean deletePet(int id);            // 删除宠物
}
