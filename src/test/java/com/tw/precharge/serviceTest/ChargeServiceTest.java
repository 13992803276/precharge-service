package com.tw.precharge.serviceTest;

import com.tw.precharge.controller.dto.ChargeDTO;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.RentUser;
import com.tw.precharge.infrastructure.httpInterface.WechatPayClient;
import com.tw.precharge.infrastructure.mqService.kafka.KafkaSender;
import com.tw.precharge.infrastructure.repository.ChargementRepository;
import com.tw.precharge.infrastructure.repository.RefundmentRepository;
import com.tw.precharge.infrastructure.repository.UserRepository;
import com.tw.precharge.service.ChargeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ChargeServiceTest {

    private  UserRepository userRepository;
    private  ChargementRepository chargementRepository;
    private  RefundmentRepository refundmentRepository;
    private  WechatPayClient wechatPayClient;
    private  KafkaSender kafkaSender;
    private ChargeService chargeService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        chargementRepository = Mockito.mock(ChargementRepository.class);
        refundmentRepository = Mockito.mock(RefundmentRepository.class);
        wechatPayClient = Mockito.mock(WechatPayClient.class);
        kafkaSender = Mockito.mock(KafkaSender.class);
        chargeService = new ChargeService(userRepository,chargementRepository
                ,refundmentRepository,wechatPayClient,kafkaSender);
    }
    private RentUser getUser(){
        return RentUser.builder()
                .id(1)
                .name("徐乐")
                .account("wuhen057")
                .address("xian")
                .balance(new BigDecimal(0))
                .phone("13992809270")
                .status("1")
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
}