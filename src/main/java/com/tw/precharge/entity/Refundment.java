package com.tw.precharge.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author lexu
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户退款请求模型", description = "退款请求详细信息实体类")
public class Refundment {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "退款ID")
    private Integer id;
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;
    @ApiModelProperty(value = "退款日期")
    private LocalDate refundDate;
    @ApiModelProperty(value = "合约Id")
    private Integer cid;
    @ApiModelProperty(value = "退款人")
    private String refundedBy;
    @ApiModelProperty(value = "退款途径")
    private String refundType;
    @ApiModelProperty(value = "退款账号")
    private String refundAccount;
}