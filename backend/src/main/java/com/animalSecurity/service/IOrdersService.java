package com.animalSecurity.service;

import com.animalSecurity.dto.OrderDetailDTO;
import com.animalSecurity.entity.Orders;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
public interface IOrdersService extends IService<Orders> {

    Orders getOrderById(Integer orderId);                      // 根据订单 ID 查询订单详情
    boolean createOrder(Orders order);                         // 创建订单
    boolean updateOrderStatus(Integer orderId, String status,Integer vendorId); // 更新订单状态
    Page<Orders> getAllOrdersByUserId(Integer userId, int page, int size);    //分页查询所有订单
    boolean checkInsuranceExpiry(LocalDate endDate);
    OrderDetailDTO getOrderDetailByPolicyId(int orderId);
    Page<Orders> getAllOrders(Integer page, Integer size);
    OrderDetailDTO getOrderDetailByPetId(int petId);


}
