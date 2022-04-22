package com.tw.precharge.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author lexu
 */
@Data
@Builder
@ApiModel(value = "微信支付返回对象", description = "调用微信确认支付API后返回对象")
public class WechatPayDTO {
    private String code;
    private String message;
    private String data;
}
