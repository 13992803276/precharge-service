package com.tw.precharge.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lexu
 */
@Data
@ApiModel(value = "退款请求", description = "发起退款请求需要的入参")
public class RefundDTO {
    private String refundAmount;
}
