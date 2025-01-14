package com.animalSecurity.mapper;
import com.animalSecurity.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */

public interface UsersMapper extends BaseMapper<Users> {
    @Select("SELECT * FROM users WHERE username = #{username}")
    Users selectByUsername(String username);

}
