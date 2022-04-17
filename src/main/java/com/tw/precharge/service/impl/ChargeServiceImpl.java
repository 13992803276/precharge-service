package com.tw.precharge.service.impl;

import com.tw.precharge.dto.ChargeDTO;
import com.tw.precharge.dto.ResultStatus;
import com.tw.precharge.dto.WeChatPayResDTO;
import com.tw.precharge.dto.WechatPayDTO;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.User;
import com.tw.precharge.repository.ChargementRepository;
import com.tw.precharge.repository.UserRepository;
import com.tw.precharge.service.ChargeService;
import com.tw.precharge.service.WechatPayClient;
import com.tw.precharge.util.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author lexu
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

    UserRepository userRepository;

    ChargementRepository chargementRepository;

    WechatPayClient wechatPayClient;

    @Override
    public Chargement charge(ChargeDTO dto, String cid, Integer userId) {
        //1 校验用户状态是否正确
        User user = userRepository.getUserById(userId).orElse(null);
        if (new BigDecimal(dto.getAmount()).compareTo(BigDecimal.valueOf(0.0)) < 0){
            throw new BusinessException(ResultStatus.PARAM_ERROR);
        }
        if (user != null) {
            // 生成支付请求
            //保存数据库
            Chargement chargement = Chargement.builder()
                                    .id(1)
                                    .cid(Integer.parseInt(cid))
                                    .title("思沃租房平台账户预充值")
                                    .payLimit(0.5f)
                                    .chargeAmount(new BigDecimal(dto.getAmount()))
                                    .payerName(user.getName())
                                    .payerId(user.getAccount())
                                    .chargeType("wechat")
                                    .chargeAccount(dto.getWeChatId())
                                    .payeeName("思沃租房平台")
                                    .payeeId("thoughtworks")
                                    .createdBy("思沃租房平台")
                                    .createdDate(LocalDate.now()).build();
            chargementRepository.save(chargement);
            return chargement;
        } else {
            throw new BusinessException(ResultStatus.USER_ERROR);
        }
    }

    @Override
    public String chargeConfirmation(String cid, String rid) {
        Chargement chargementById = chargementRepository.getChargementById(Integer.parseInt(rid));
        if (chargementById != null){
            WeChatPayResDTO resDTO = WeChatPayResDTO.builder()
                    .wechatId(chargementById.getPayerId())
                    .rid(rid)
                    .cid(cid).build();
            WechatPayDTO charge = wechatPayClient.charge(resDTO);
            return charge.getMessage();
        }
        return "success";
    }

    @Override
    public List<Chargement> charge(Integer cid) {
        return chargementRepository.getChargementByCid(cid);
    }
}
