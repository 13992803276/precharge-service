package com.tw.precharge.infrastructure.httpInterface;

import com.tw.precharge.controller.dto.WeChatPayResDTO;
import com.tw.precharge.controller.dto.WechatPayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lexu
 */
@Service
public class WechatService {

    @Autowired
    WechatPayClient wechatPayClient;

    public WechatPayDTO payment(WeChatPayResDTO weChatPayResDTO){
        return wechatPayClient.payment(weChatPayResDTO);
    }

    public WechatPayDTO refund(WeChatPayResDTO wechatPayDTO){
        return wechatPayClient.refund(wechatPayDTO);
    }
}
