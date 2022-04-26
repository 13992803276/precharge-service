package com.tw.precharge.serviceTest;

import com.tw.precharge.constant.PayStatus;
import com.tw.precharge.controller.dto.ChargeDTO;
import com.tw.precharge.controller.dto.WechatPayDTO;
import com.tw.precharge.domain.user.RentUser;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.infrastructure.httpInterface.WechatService;
import com.tw.precharge.infrastructure.mqService.kafka.KafkaSender;
import com.tw.precharge.infrastructure.repository.ChargementRepository;
import com.tw.precharge.infrastructure.repository.RefundmentRepository;
import com.tw.precharge.infrastructure.repository.RentUserRepository;
import com.tw.precharge.service.ChargeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ChargeServiceTest {

    private RentUserRepository userRepository;
    private  ChargementRepository chargementRepository;
    private  RefundmentRepository refundmentRepository;
    private WechatService wechatService;
    private  KafkaSender kafkaSender;
    private ChargeService chargeService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(RentUserRepository.class);
        chargementRepository = Mockito.mock(ChargementRepository.class);
        refundmentRepository = Mockito.mock(RefundmentRepository.class);
        wechatService = Mockito.mock(WechatService.class);
        kafkaSender = Mockito.mock(KafkaSender.class);
        chargeService = new ChargeService(userRepository,chargementRepository
                ,refundmentRepository,wechatService,kafkaSender);
    }
    private RentUser getUser(){
        return RentUser.builder()
                .id(1)
                .name("徐乐")
                .account("wuhen057")
                .address("xian")
                .balance(new BigDecimal(0))
                .phone("13992809270")
                .status("0")
                .created(LocalDate.now())
                .build();
    }

    @Test
    public void given_user_is_active_and_amount_should_return_chargement() {
        ChargeDTO chargeDTO = ChargeDTO.builder()
                .amount("20.0")
                .weChatId("wuhen057")
                .build();
        when(userRepository.getUserById(any())).thenReturn(Optional.ofNullable(getUser()));
        Chargement chargement = chargeService.charge(chargeDTO, "12", any());

        Assertions.assertEquals(chargement.getChargeAmount(), new BigDecimal("20.0"));
        Assertions.assertEquals(chargement.getChargeAccount(), "wuhen057");
    }

    @Test
    public void given_user_is_active_and_amount_should_charge_confirm_surely(){
        Chargement chargement = Chargement.builder()
                .cid(1)
                .title("思沃租房平台账户预充值")
                .payLimit(0.5f)
                .chargeAmount(new BigDecimal("100"))
                .payerName("xule")
                .payerId("wuhen057")
                .chargeType("wechat")
                .chargeAccount("wuhen057")
                .payeeName("思沃租房平台")
                .payeeId("thoughtworks")
                .createdBy("思沃租房平台")
                .createdDate(LocalDate.now())
                .status(PayStatus.STAY_PAY.getCode())
                .build();
        WechatPayDTO wechatPayDTO = WechatPayDTO.builder()
                .code("200")
                .message("success")
                .data("37488372nie2ekf994")
                .build();
        when(chargementRepository.getChargementById(any())).thenReturn(chargement);
        when(wechatService.payment(any())).thenReturn(wechatPayDTO);
        when(chargementRepository.save(any())).thenReturn(null);
        when(userRepository.getRentUserByAccount(anyString())).thenReturn(Optional.ofNullable(getUser()));
        when(userRepository.save(any())).thenReturn(null);
        String result = chargeService.chargeConfirmation("12", "1");
        Assertions.assertEquals(result, "success");
    }
}