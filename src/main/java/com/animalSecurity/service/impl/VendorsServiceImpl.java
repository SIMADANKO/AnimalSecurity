package com.animalSecurity.service.impl;

import com.animalSecurity.entity.Vendors;
import com.animalSecurity.mapper.VendorsMapper;
import com.animalSecurity.service.IVendorsService;
import com.animalSecurity.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商家表 服务实现类
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@Service
public class VendorsServiceImpl extends ServiceImpl<VendorsMapper, Vendors> implements IVendorsService {
    @Autowired
    private VendorsMapper vendorMapper;

    @Autowired
    private JwtUtil jwtUtils;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean registerVendor(Vendors vendor) {
        // 判断商家名称是否已经存在
        QueryWrapper<Vendors> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vendor_name", vendor.getVendorName());
        Vendors existingVendor = vendorMapper.selectOne(queryWrapper);
        if (existingVendor != null) {
            return false; // 商家名称已存在
        }

        // 商家注册时可以添加密码字段，示例中不添加
        return vendorMapper.insert(vendor) > 0;
    }

    @Override
    public String loginVendor(String vendorName, String password) {
        // 查询商家
        QueryWrapper<Vendors> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vendor_name", vendorName);
        Vendors vendor = vendorMapper.selectOne(queryWrapper);

        if (vendor != null && passwordEncoder.matches(password, vendor.getPassword())) {
            // 生成JWT token
            return jwtUtils.generateToken(vendor.getVendorId().toString());
        }
        return null; // 登录失败
    }

    @Override
    public Vendors getVendorById(Integer vendorId) {
        return vendorMapper.selectById(vendorId);
    }

    @Override
    public boolean updateVendor(Vendors vendor) {
        return vendorMapper.updateById(vendor) > 0;
    }

}
