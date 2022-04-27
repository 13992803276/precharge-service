package com.tw.precharge.httpInterfaceTest;

import com.tw.precharge.controller.dto.WeChatPayResDTO;
import com.tw.precharge.controller.dto.WechatPayDTO;
import com.tw.precharge.infrastructure.httpInterface.WechatPayClient;
import com.tw.precharge.infrastructure.httpInterface.WechatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WechatPayClientTest {
    @InjectMocks
    WechatService wechatService;
    @Mock
    WechatPayClient wechatPayClient = Mockito.mock(WechatPayClient.class);

    WeChatPayResDTO weChatPayResDTO = WeChatPayResDTO.builder()
            .wechatId("wuhen057")
            .amount("100")
            .build();
    @Captor
    ArgumentCaptor<WeChatPayResDTO> argumentCaptor = ArgumentCaptor.forClass(WeChatPayResDTO.class);

    @Test
    void test_wechat_client_should_return_successful_result(){
        WechatPayDTO wechatPayDTO = WechatPayDTO.builder()
                .code("200")
                .message("success")
                .data("37488372nie2ekf994")
                .build();
        Mockito.when(wechatPayClient.payment(any())).thenReturn(wechatPayDTO);
        WechatPayDTO payment = wechatService.payment(weChatPayResDTO);
        Assertions.assertEquals(payment.getCode(),"200");
        Assertions.assertEquals(payment.getMessage(),"success");
        Assertions.assertEquals(payment.getData(),"37488372nie2ekf994");
    }

    @Test
    void test_wechat_client_should_get_pram_success(){
        wechatService.payment(weChatPayResDTO);
        verify(wechatPayClient, times(1)).payment(argumentCaptor.capture());
        Assertions.assertEquals(argumentCaptor.getValue().getWechatId(),"wuhen057");
        Assertions.assertEquals(argumentCaptor.getValue().getAmount(),"100");
    }
}