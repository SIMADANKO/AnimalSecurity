package com.animalSecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 商家表
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@ApiModel(value = "Vendors对象", description = "商家表")
public class Vendors implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商家ID")
    @TableId(value = "vendor_id", type = IdType.AUTO)
    private Integer vendorId;

    @ApiModelProperty("商家名称")
    private String vendorName;

    @ApiModelProperty("联系邮箱")
    private String contactEmail;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("商家地址")
    private String address;

    @ApiModelProperty("记录创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("记录更新时间")
    private LocalDateTime updateTime;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return "Vendors{" +
            "vendorId = " + vendorId +
            ", vendorName = " + vendorName +
            ", contactEmail = " + contactEmail +
            ", contactPhone = " + contactPhone +
            ", address = " + address +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
