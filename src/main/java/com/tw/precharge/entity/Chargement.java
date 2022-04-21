package com.tw.precharge.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@ApiModel(value = "用户充值请求模型", description = "充值请求详细信息实体类")
@Table(name = "chargement")
public class Chargement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "充值Id")
    private Integer id;
    @ApiModelProperty(value = "充值请求主题")
    private String title;
    @ApiModelProperty(value = "充值充值合约Id")
    private Integer cid;
    @ApiModelProperty(value = "充值金额")
    private BigDecimal chargeAmount;
    @ApiModelProperty(value = "支付有效时间（单位：小时）")
    private Float payLimit;
    @ApiModelProperty(value = "充值支付类型")
    private String chargeType;
    @ApiModelProperty(value = "充值支付账号")
    private String chargeAccount;
    @ApiModelProperty(value = "支付方Id")
    private String payerId;
    @ApiModelProperty(value = "支付方名称")
    private String payerName;
    @ApiModelProperty(value = "收款方名称")
    private String payeeName;
    @ApiModelProperty(value = "收款方Id")
    private String payeeId;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "充值创建日期")
    @CreatedDate
    private LocalDate createdDate;


    public String getObject(){
        return "您有一条待支付请求需要支付，请您在" + this.payLimit +
                "小时之内通过" + this.chargeType  + "进行支付。\n" +
                "详细信息：\n" +
                "费用项：" + this.title + "\n" +
                "费用金额：" + this.chargeAmount + "\n" +
                "费用时间：" + this.createdDate + "\n" +
                "收费方：" + this.createdBy;
    }
}
