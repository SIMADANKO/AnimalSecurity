package com.animalSecurity.service.impl;

import com.animalSecurity.entity.Orders;
import com.animalSecurity.mapper.OrdersMapper;
import com.animalSecurity.service.IOrdersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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


    public Page<Orders> getAllOrdersByUserId(Integer userId, int page, int size) {
        // 创建 Page 对象，page 表示当前页，size 表示每页大小
        Page<Orders> pageParam = new Page<>(page, size);

        // 使用 MyBatis-Plus 提供的分页查询方法
        return orderMapper.selectPage(pageParam,
                new QueryWrapper<Orders>().eq("user_id", userId));
    }



    @Override
    public boolean checkInsuranceExpiry(LocalDate endDate) {
        // 判断当前日期是否大于保险的结束日期
        return LocalDate.now().isAfter(endDate);
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
