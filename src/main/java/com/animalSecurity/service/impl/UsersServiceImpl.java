package com.animalSecurity.service.impl;

import com.animalSecurity.entity.Users;
import com.animalSecurity.mapper.UsersMapper;
import com.animalSecurity.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
