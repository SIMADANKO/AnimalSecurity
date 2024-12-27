package com.animalSecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@ApiModel(value = "Users对象", description = "用户表")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码（需加密存储）")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

//    @ApiModelProperty("联系电话")
//    private String phone;
//
//    @ApiModelProperty("联系地址")
//    private String address;
//
//    @ApiModelProperty("记录创建时间")
//    private LocalDateTime createTime;
//
//    @ApiModelProperty("记录更新时间")
//    private LocalDateTime updateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public LocalDateTime getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(LocalDateTime createTime) {
//        this.createTime = createTime;
//    }
//
//    public LocalDateTime getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(LocalDateTime updateTime) {
//        this.updateTime = updateTime;
//    }

    @Override
    public String toString() {
        return "Users{" +
            "userId = " + userId +
            ", username = " + username +
            ", password = " + password +
            ", email = " + email +
//            ", phone = " + phone +
//            ", address = " + address +
//            ", createTime = " + createTime +
//            ", updateTime = " + updateTime +
        "}";
    }
}
