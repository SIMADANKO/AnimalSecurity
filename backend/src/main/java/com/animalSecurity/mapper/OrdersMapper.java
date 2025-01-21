package com.animalSecurity.mapper;
import com.animalSecurity.dto.OrderDetailDTO;
import com.animalSecurity.entity.Orders;
import com.animalSecurity.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */

public interface OrdersMapper extends BaseMapper<Orders> {
    @Select("SELECT * FROM orders WHERE pet_Id = #{petId}")
    OrderDetailDTO selectByPetId(int petId);
}
