package com.tw.precharge.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author lexu
 */

@Data
@Builder
@ApiModel(value = "微信支付请求对象", description = "调用微信确认支付API后请求对象")
public class WeChatPayResDTO {

    private String wechatId;
    private String cid;
    private String rid;
}
