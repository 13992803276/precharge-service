package com.tw.precharge.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author lexu
 */
@Data
@Builder
@ApiModel(value = "充值请求", description = "发起充值请求需要的入参")
public class ChargeDTO {
    private String weChatId;
    private String amount;
}
