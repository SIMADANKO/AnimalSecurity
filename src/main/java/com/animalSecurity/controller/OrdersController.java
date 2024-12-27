package com.animalSecurity.controller;

import com.animalSecurity.entity.Orders;
import com.animalSecurity.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrdersService orderService;

    // 用户：查询当前用户的所有订单
    @GetMapping
    public List<Orders> getAllOrders(Authentication authentication) {
        // 从 Authentication 获取当前用户的 ID
        Integer userId = Integer.parseInt(authentication.getName());
        return orderService.getAllOrdersByUserId(userId);
    }

    // 用户：查看订单详情
    @GetMapping("/{id}")
    public Orders getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    // 用户：创建新订单
    @PostMapping
    public String createOrder(@RequestBody Orders order, Authentication authentication) {
        // 从 Authentication 获取当前用户的 ID
        Integer userId = Integer.parseInt(authentication.getName());
        order.setOrderStatus("Pending");
        order.setUserId(userId); // 设置用户 ID
        if (orderService.createOrder(order)) {
            return "Order created successfully!";
        }
        return "Failed to create order.";
    }

    // 管理员：更新订单状态
    @PutMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Integer id,
                                    @RequestParam String status,
                                    Authentication authentication) {
        Integer vendorId = Integer.parseInt(authentication.getName());
        if (orderService.updateOrderStatus(id, status, vendorId)) {
            return "Order status updated successfully!";
        }
        return "Failed to update order status.";
    }
}
