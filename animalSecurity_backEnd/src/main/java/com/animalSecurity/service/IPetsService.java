package com.animalSecurity.service;

import com.animalSecurity.entity.Pets;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    Page<Pets> getPetsByUserId(Integer userId, Page<Pets> page);// 查询所有宠物

    boolean addPet(Pets pet);              // 添加宠物

    boolean updatePet(Pets pet);           // 更新宠物信息

    boolean deletePet(int id);            // 删除宠物

    boolean updateInsuranceStatus(Integer petId, String status);
}
