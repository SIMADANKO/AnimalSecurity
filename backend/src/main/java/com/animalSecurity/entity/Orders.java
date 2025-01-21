package com.animalSecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@ApiModel(value = "Orders对象", description = "订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单ID")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    @ApiModelProperty("用户ID，外键关联用户表")
    private Integer userId;

    @ApiModelProperty("宠物ID，外键关联宠物表")
    private Integer petId;

    @ApiModelProperty("商家ID，外键关联商家表")
    private Integer vendorId;

    @ApiModelProperty("保险产品ID")
    private Integer policyId;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    @ApiModelProperty("订单总金额")
    private BigDecimal totalPrice;

    @ApiModelProperty("保险开始日期")
    private LocalDate startDate;

    @ApiModelProperty("保险结束日期")
    private LocalDate endDate;

    @ApiModelProperty("订单创建时间")
    private LocalDate createTime;

    @ApiModelProperty("订单更新时间")
    private LocalDate updateTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdate() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", petId=" + petId +
                ", vendorId=" + vendorId +
                ", policyId=" + policyId +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalPrice=" + totalPrice +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
