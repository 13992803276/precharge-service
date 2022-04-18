package com.tw.precharge.service;

import com.tw.precharge.controller.configuration.FeignConfiguration;
import com.tw.precharge.dto.WeChatPayResDTO;
import com.tw.precharge.dto.WechatPayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lexu
 */
@FeignClient(name = "rwf-identity", configuration = FeignConfiguration.class, url = "https://weixin.com/pay-ment")
public interface WechatPayClient {

    @PostMapping(value = "/v1/weChat/charge")
    WechatPayDTO charge(@RequestBody WeChatPayResDTO weChatPayResDTO);

}
