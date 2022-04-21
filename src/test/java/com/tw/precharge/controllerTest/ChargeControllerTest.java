package com.tw.precharge.controllerTest;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.util.List;
import com.tw.precharge.PrechargeServiceApplication;
import com.tw.precharge.constant.PayStatus;
import com.tw.precharge.controller.dto.ChargeDTO;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.service.ChargeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PrechargeServiceApplication.class)
public class ChargeControllerTest {

    @Mock
    ChargeService chargeService = Mockito.mock(ChargeService.class);

    @Autowired
    MockMvc mockMvc ;
    Chargement chargement = Chargement.builder()
            .cid(12)
            .title("思沃租房平台账户预充值")
            .payLimit(0.5f)
            .chargeAmount(new BigDecimal("100.0"))
            .payerName("xule")
            .payerId("wuhen")
            .chargeType("wechat")
            .chargeAccount("wuhen057")
            .payeeName("思沃租房平台")
            .payeeId("thoughtworks")
            .createdBy("思沃租房平台")
            .createdDate(LocalDate.now())
            .status(PayStatus.STAY_PAY.getCode())
            .build();
    @Test
    public void test_charge_request_with_post_request() throws Exception {
        ChargeDTO chargeDTO = ChargeDTO.builder().weChatId("wuhen000").amount("100,0").build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(chargeDTO);

        Mockito.when(chargeService.charge(any(),anyString(),anyInt())).thenReturn(chargement);
        MockHttpServletResponse response = mockMvc.perform(post("http://localhost:8080/precharge_contract/12/charge")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        //验证controller 获取到的对象是否和service层返回的对象一致。
        String str = response.getContentAsString(StandardCharsets.UTF_8);
        Chargement chargement1 = JSON.parseObject(JSON.parseObject(str).getJSONArray("data").get(0).toString(), Chargement.class);
        Assertions.assertEquals(chargement.getChargeAmount(), chargement1.getChargeAmount());
    }

    @Test
    public void test_charge_with_get_request() throws Exception {
        Mockito.when(chargeService.charge(anyInt())).thenReturn(List.of(chargement));
        MockHttpServletResponse response = mockMvc.perform(get("http://localhost:8080/precharge_contract/12/charge")
                        .param("cid","12"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        String str = response.getContentAsString(StandardCharsets.UTF_8);
        Chargement chargement1 = JSON.parseObject(JSON.parseObject(str).getJSONArray("data").get(0).toString(), Chargement.class);
        Assertions.assertEquals(chargement.getChargeAmount(), chargement1.getChargeAmount());

    }
}