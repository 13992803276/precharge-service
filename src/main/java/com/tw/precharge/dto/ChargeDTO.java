package com.tw.precharge.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lexu
 */
@Data
@ApiModel(value = "充值请求", description = "发起充值请求需要的入参")
public class ChargeDTO {
    private String weChatId;
    private String amount;
}
