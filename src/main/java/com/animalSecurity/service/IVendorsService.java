package com.animalSecurity.service;

import com.animalSecurity.entity.Vendors;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商家表 服务类
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
public interface IVendorsService extends IService<Vendors> {
    // 商家注册
    boolean registerVendor(Vendors vendor);

    // 商家登录，返回JWT token
    String loginVendor(String vendorName, String password);

    // 获取商家信息
    Vendors getVendorById(Integer vendorId);

    // 更新商家个人信息
    boolean updateVendor(Vendors vendor);
}
