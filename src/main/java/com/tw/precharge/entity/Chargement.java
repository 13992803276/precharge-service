package com.tw.precharge.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author lexu
 */
@Data
@Builder
public class Chargement {
    private Integer id;
    private String title;
    private Integer cid;
    private BigDecimal chargeAmount;
    private Float payLimit;
    private String chargeType;
    private String chargeAccount;
    private String payerId;
    private String payerName;
    private String payeeName;
    private String payeeId;
    private String status;
    private String createdBy;
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
