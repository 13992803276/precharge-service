package com.tw.precharge.serviceTest;

import com.tw.precharge.dto.ChargeDTO;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.User;
import com.tw.precharge.httpInterface.WechatPayClient;
import com.tw.precharge.mq.kafka.KafkaSender;
import com.tw.precharge.repository.ChargementRepository;
import com.tw.precharge.repository.RefundmentRepository;
import com.tw.precharge.repository.UserRepository;
import com.tw.precharge.service.impl.ChargeServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ChargeServiceTest {

    private  UserRepository userRepository;
    private  ChargementRepository chargementRepository;
    private  RefundmentRepository refundmentRepository;
    private  WechatPayClient wechatPayClient;
    private  KafkaSender kafkaSender;
    private  ChargeServiceImpl chargeServiceimpl;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        chargementRepository = Mockito.mock(ChargementRepository.class);
        refundmentRepository = Mockito.mock(RefundmentRepository.class);
        wechatPayClient = Mockito.mock(WechatPayClient.class);
        kafkaSender = Mockito.mock(KafkaSender.class);
        chargeServiceimpl = new ChargeServiceImpl(userRepository,chargementRepository
                ,refundmentRepository,wechatPayClient,kafkaSender);
    }
    private User getUser(){
        return User.builder()
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
        when(userRepository.getUserById(1)).thenReturn(Optional.ofNullable(getUser()));
        Chargement chargement = chargeServiceimpl.charge(chargeDTO, "12", 1);
        Assertions.assertEquals(chargement.getChargeAccount(), "20.0");
    }
}