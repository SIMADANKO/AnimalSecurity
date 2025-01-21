package com.animalSecurity.service.impl;

import com.animalSecurity.dto.OrderDetailDTO;
import com.animalSecurity.entity.Orders;
import com.animalSecurity.entity.Pets;
import com.animalSecurity.entity.Policy;
import com.animalSecurity.mapper.OrdersMapper;
import com.animalSecurity.mapper.PetsMapper;
import com.animalSecurity.mapper.PolicyMapper;
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
    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private PetsMapper petsMapper;


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

    public OrderDetailDTO getOrderDetailByPolicyId(int orderId) {
        Orders order = orderMapper.selectById(orderId);

        // 先查询保险表中的保险产品信息
        Policy policy = policyMapper.selectById(order.getPolicyId());
        if (policy == null) {
            return null;
        }

        // 查询宠物表，假设保险产品和宠物表通过 policyId 进行关联
        Pets pet = petsMapper.selectById(order.getPetId());
        if (pet == null) {
            return null;
        }

        // 将数据封装成 OrderDetailDTO 对象
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setPetName(pet.getPetName());
        orderDetailDTO.setPolicyName(policy.getPolicyName());
        orderDetailDTO.setPrice(policy.getPremium());
        orderDetailDTO.setOrderId(orderId);
        orderDetailDTO.setOrderStatus(order.getOrderStatus());
        orderDetailDTO.setPetId(pet.getPetId());
        orderDetailDTO.setPolicyId(policy.getPolicyId());
        orderDetailDTO.setStartDate(order.getStartDate());

        orderDetailDTO.setUpdateTime(order.getUpdateTime());
        orderDetailDTO.setUserId(order.getUserId());
        orderDetailDTO.setVendorId(order.getVendorId());

        return orderDetailDTO;
    }


    @Override
    public Page<Orders> getAllOrders(Integer page, Integer size) {
        // 使用 MyBatis-Plus 提供的分页功能
        Page<Orders> pageInfo = new Page<>(page, size);

        // 调用 MyBatis-Plus 提供的分页查询方法
        orderMapper.selectPage(pageInfo, null);

        return pageInfo;
    }

}
