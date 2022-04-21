package com.tw.precharge.infrastructure.httpInterface;

import com.tw.precharge.controller.configuration.FeignConfiguration;
import com.tw.precharge.dto.WeChatPayResDTO;
import com.tw.precharge.dto.WechatPayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lexu
 */
@FeignClient(name = "wechat-payment-service", configuration = FeignConfiguration.class, url = "https://weixin.com/pay-ment")
public interface WechatPayClient {

    @PostMapping(value = "/v1/weChat/charge")
    WechatPayDTO payment(@RequestBody WeChatPayResDTO weChatPayResDTO);

    @PostMapping(value = "/v1/weChat/refund")
    WechatPayDTO refund(@RequestBody WeChatPayResDTO weChatPayResDTO);

}
