package com.animalSecurity.controller;
import com.animalSecurity.config.CustomUserDetails;
import com.animalSecurity.dto.OrderDetailDTO;
import com.animalSecurity.entity.Orders;
import com.animalSecurity.entity.Policy;
import com.animalSecurity.lang.Result;
import com.animalSecurity.service.IOrdersService;
import com.animalSecurity.service.IPetsService;
import com.animalSecurity.service.IPolicyService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;



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

    @Autowired
    private IPolicyService policyService;

    @Autowired
    private IPetsService petsService;

    // 用户：查询当前用户的所有订单
    @GetMapping
    public Result<Page<Orders>> getAllOrders(
            @RequestParam Integer page,
            @RequestParam Integer size,
            Authentication authentication) {

        // 从 Authentication 获取当前用户的 userId
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        // 调用服务层方法进行分页查询
        Page<Orders> ordersPage = orderService.getAllOrdersByUserId(userId, page, size);

        if (ordersPage != null && !ordersPage.getRecords().isEmpty()) {
            return Result.success(ordersPage);
        }
        return Result.fail(404, "No orders found for the current user.");
    }

    // 用户：查看订单详情
    @GetMapping("/{id}")
    public Result<OrderDetailDTO> getOrderById(@PathVariable Integer id) {

        OrderDetailDTO order = orderService.getOrderDetailByPolicyId(id); // 使用 orderId 查询
        if (order != null) {
            return Result.success(order);
        }
        return Result.fail(404, "Order not found.");
    }

    // 用户：创建新订单
    @PostMapping
    public Result<String> createOrder(@RequestBody Orders order, Authentication authentication) {
        // 从 Authentication 获取当前用户的 userId
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        order.setOrderStatus("Pending");
        order.setUserId(userId); // 设置 userId

        Policy policy = policyService.getById(order.getPolicyId());

        order.setTotalPrice(policy.getPremium());

        order.setStartDate(LocalDate.now());

        int termsMonth=policy.getTermMonths();

        LocalDate endDate = LocalDate.now().plusMonths(termsMonth);

        order.setEndDate(endDate);

        boolean success = orderService.createOrder(order);
        if (success) {

            // 更新宠物的参保状态
            boolean petUpdateSuccess = petsService.updateInsuranceStatus(order.getPetId(), "active");

            if (petUpdateSuccess) {
                return Result.success("Order created and pet insurance status updated successfully!");
            } else {
                // 如果更新宠物参保状态失败，返回订单创建成功，但参保状态更新失败的消息
                return Result.success("Order created successfully, but failed to update pet insurance status.");
            }

        }
        return Result.fail(500, "Failed to create order.");
    }

    // 管理员：更新订单状态
    @PutMapping("/{id}/status")
    public Result<String> updateOrderStatus(@PathVariable Integer id,
                                            @RequestParam String status,
                                            Authentication authentication) {
        // 管理员身份使用 userId
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        boolean success = orderService.updateOrderStatus(id, status, userId); // 使用 userId 更新订单
        if (success) {
            return Result.success("Order status updated successfully!");
        }
        return Result.fail(500, "Failed to update order status.");
    }
}