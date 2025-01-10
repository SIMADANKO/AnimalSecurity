package com.animalSecurity.controller;
import com.animalSecurity.entity.Orders;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IOrdersService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrdersService orderService;

    // 用户：查询当前用户的所有订单
    @GetMapping
    public Result<Page<Orders>> getAllOrders(
            @RequestParam Integer page,
            @RequestParam Integer size,
            Authentication authentication) {

        // 从 Authentication 获取当前用户的 ID
        Integer userId = Integer.parseInt(authentication.getName());

        // 调用服务层方法进行分页查询
        Page<Orders> ordersPage = orderService.getAllOrdersByUserId(userId, page, size);

        if (ordersPage != null && !ordersPage.getRecords().isEmpty()) {
            return Result.success(ordersPage);
        }
        return Result.fail(404, "No orders found for the current user.");
    }

    // 用户：查看订单详情
    @GetMapping("/{id}")
    public Result<Orders> getOrderById(@PathVariable Integer id) {
        Orders order = orderService.getOrderById(id);
        if (order != null) {
            return Result.success(order);
        }
        return Result.fail(404, "Order not found.");
    }

    // 用户：创建新订单
    @PostMapping
    public Result<String> createOrder(@RequestBody Orders order, Authentication authentication) {
        // 从 Authentication 获取当前用户的 ID
        Integer userId = Integer.parseInt(authentication.getName());
        order.setOrderStatus("Pending");
        order.setUserId(userId); // 设置用户 ID
        boolean success = orderService.createOrder(order);
        if (success) {
            return Result.success("Order created successfully!");
        }
        return Result.fail(500, "Failed to create order.");
    }

    // 管理员：更新订单状态
    @PutMapping("/{id}/status")
    public Result<String> updateOrderStatus(@PathVariable Integer id,
                                            @RequestParam String status,
                                            Authentication authentication) {
        Integer vendorId = Integer.parseInt(authentication.getName());
        boolean success = orderService.updateOrderStatus(id, status, vendorId);
        if (success) {
            return Result.success("Order status updated successfully!");
        }
        return Result.fail(500, "Failed to update order status.");
    }
}