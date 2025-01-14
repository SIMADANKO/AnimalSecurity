package com.animalSecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 保险产品表
 * </p>
 *
 * @author lu
 * @since 2024-12-26
 */
@ApiModel(value = "Policy对象", description = "保险产品表")
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("保险产品ID")
    @TableId(value = "policy_id", type = IdType.AUTO)
    private Integer policyId;

    @ApiModelProperty("保险产品名称")
    private String policyName;

    @ApiModelProperty("保险产品描述")
    private String description;

    @ApiModelProperty("保费")
    private BigDecimal premium;

    @ApiModelProperty("保险覆盖金额")
    private BigDecimal coverage;

    @ApiModelProperty("保险期限（月）")
    private Integer termMonths;

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getCoverage() {
        return coverage;
    }

    public void setCoverage(BigDecimal coverage) {
        this.coverage = coverage;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    @Override
    public String toString() {
        return "Policy{" +
            "policyId = " + policyId +
            ", policyName = " + policyName +
            ", description = " + description +
            ", premium = " + premium +
            ", coverage = " + coverage +
            ", termMonths = " + termMonths +
        "}";
    }
}
