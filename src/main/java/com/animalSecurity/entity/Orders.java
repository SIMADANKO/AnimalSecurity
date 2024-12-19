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

    @ApiModelProperty("宠物ID，外键关联宠物表")
    private Integer petId;

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
    private LocalDateTime createTime;

    @ApiModelProperty("订单更新时间")
    private LocalDateTime updateTime;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Orders{" +
            "orderId = " + orderId +
            ", petId = " + petId +
            ", policyId = " + policyId +
            ", orderStatus = " + orderStatus +
            ", totalPrice = " + totalPrice +
            ", startDate = " + startDate +
            ", endDate = " + endDate +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
