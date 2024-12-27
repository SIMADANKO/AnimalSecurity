package com.animalSecurity.service.impl;

import com.animalSecurity.entity.Orders;
import com.animalSecurity.mapper.OrdersMapper;
import com.animalSecurity.service.IOrdersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    @Autowired
    private OrdersMapper orderMapper;

    @Override
    public List<Orders> getAllOrdersByUserId(Integer userId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return orderMapper.selectList(queryWrapper); // 查询用户的所有订单
    }

    @Override
    public Orders getOrderById(Integer orderId) {
        return orderMapper.selectById(orderId); // 根据订单 ID 查询订单详情
    }

    @Override
    public boolean createOrder(Orders order) {
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return orderMapper.insert(order) > 0; // 插入订单数据
    }

    @Override
    public boolean updateOrderStatus(Integer orderId, String status, Integer vendorId) {
        Orders order = orderMapper.selectById(orderId);
        if (order != null) {
            order.setOrderStatus(status);
            order.setVendorId(vendorId); // 添加管理员 ID
            order.setUpdateTime(LocalDateTime.now());
            return orderMapper.updateById(order) > 0;
        }
        return false;
    }
}
