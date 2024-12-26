package com.animalSecurity.service.impl;

import com.animalSecurity.entity.Users;
import com.animalSecurity.mapper.UsersMapper;
import com.animalSecurity.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Autowired
    private UsersMapper usersMapper;

    // 用户注册
    public boolean register(Users user) {
        if (usersMapper.selectByUsername(user.getUsername()) != null) {
            return false; // 如果用户名已存在，注册失败
        }
        usersMapper.insert(user); // 执行插入操作
        return true;
    }

    // 发送密码重置链接
    public boolean sendPasswordResetLink(String username) {
        // 根据用户名查询用户信息
        Users user = usersMapper.selectByUsername(username);
        if (user != null) {
            // 发送邮件或者生成重置链接
            // 这里暂时模拟发送
            return true;
        }
        return false;
    }

    // 更新用户信息
    public boolean updateUserInfo(Users user) {
        int updatedRows = usersMapper.updateById(user);
        return updatedRows > 0;
    }
}
