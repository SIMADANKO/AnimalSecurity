package com.animalSecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 宠物表
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */
@ApiModel(value = "Pets对象", description = "宠物表")
public class Pets implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("宠物ID")
    @TableId(value = "pet_id", type = IdType.AUTO)
    private Integer petId;

    @ApiModelProperty("用户ID，外键关联用户表")
    private Integer userId;

    @ApiModelProperty("宠物名字")
    private String petName;

    @ApiModelProperty("宠物种类（如狗、猫）")
    private String species;

    @ApiModelProperty("宠物品种")
    private String breed;

    @ApiModelProperty("宠物年龄")
    private Integer age;

    @ApiModelProperty("宠物性别")
    private String gender;



    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {
        return "Pets{" +
            "petId = " + petId +
            ", userId = " + userId +
            ", petName = " + petName +
            ", species = " + species +
            ", breed = " + breed +
            ", age = " + age +
            ", gender = " + gender +

        "}";
    }
}
