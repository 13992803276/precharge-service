package com.tw.precharge.service.impl;

import com.tw.precharge.constant.PayStatus;
import com.tw.precharge.constant.RefundStatus;
import com.tw.precharge.dto.ChargeDTO;
import com.tw.precharge.dto.RefundDTO;
import com.tw.precharge.dto.ResultStatus;
import com.tw.precharge.dto.WeChatPayResDTO;
import com.tw.precharge.dto.WechatPayDTO;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.Refundment;
import com.tw.precharge.entity.User;
import com.tw.precharge.mq.kafka.KafkaSender;
import com.tw.precharge.repository.ChargementRepository;
import com.tw.precharge.repository.RefundmentRepository;
import com.tw.precharge.repository.UserRepository;
import com.tw.precharge.service.ChargeService;
import com.tw.precharge.httpInterface.WechatPayClient;
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

    private final UserRepository userRepository;
    private final ChargementRepository chargementRepository;
    private final RefundmentRepository refundmentRepository;
    private final WechatPayClient wechatPayClient;
    private final KafkaSender kafkaSender;

    @Override
    public Chargement charge(ChargeDTO dto, String cid, Integer userId) {
        if (new BigDecimal(dto.getAmount()).compareTo(BigDecimal.valueOf(0.0)) < 0) {
            throw new BusinessException(ResultStatus.PARAM_ERROR);
        }
        //1 校验用户状态是否正确
        User user = userRepository.getUserById(userId).orElse(null);
        if (user != null) {
            //保存数据库
            Chargement chargement = Chargement.builder()
                    .cid(Integer.parseInt(cid))
                    .title("思沃租房平台账户预充值")
                    .payLimit(0.5f)
                    .chargeAmount(new BigDecimal(dto.getAmount()))
                    .payerName(user.getName())
                    .payerId(user.getAccount())
                    .chargeType("wechat")
                    .chargeAccount(dto.getWeChatId())
                    .payeeName("思沃租房平台")
                    .chargeAccount(user.getAccount())
                    .payeeId("thoughtworks")
                    .createdBy("思沃租房平台")
                    .createdDate(LocalDate.now())
                    .status(PayStatus.STAY_PAY.getCode())
                    .build();
            chargementRepository.save(chargement);
            return chargement;
        } else {
            throw new BusinessException(ResultStatus.USER_ERROR);
        }
    }

    @Override
    public String chargeConfirmation(String cid, String rid) {
        Chargement chargementById = chargementRepository.getChargementById(Integer.parseInt(rid));
        if (chargementById != null) {
            WeChatPayResDTO resDTO = WeChatPayResDTO.builder()
                    .wechatId(chargementById.getPayerId())
                    .rid(rid)
                    .cid(cid).build();
            WechatPayDTO charge = wechatPayClient.payment(resDTO);
            chargementById.setStatus(PayStatus.PAID.getCode());
            chargementRepository.save(chargementById);
            return charge.getMessage();
        }
        return "success";
    }

    @Override
    public List<Chargement> charge(Integer cid) {
        return chargementRepository.getChargementByCid(cid);
    }

    @Override
    public Refundment refund(RefundDTO refundDTO, String cid, Integer userId) {
        if (new BigDecimal(refundDTO.getRefundAmount()).compareTo(BigDecimal.valueOf(0.0)) < 0) {
            throw new BusinessException(ResultStatus.PARAM_ERROR);
        }
        User user = userRepository.getUserById(userId).orElse(null);
        if (user != null) {
            Refundment refundment = Refundment.builder()
                    .cid(Integer.parseInt(cid))
                    .refundAmount(new BigDecimal(refundDTO.getRefundAmount()))
                    .refundAccount(user.getAccount())
                    .refundType("wechat")
                    .refundedBy(user.getName())
                    .refundDate(LocalDate.now())
                    .status(RefundStatus.STAY_REFUND.getCode())
                    .createdDate(LocalDate.now())
                    .build();
            refundmentRepository.save(refundment);
            return refundment;
        }
        throw new BusinessException(ResultStatus.USER_ERROR);
    }

    @Override
    public String refundConfirmation(String cid, String rid) {
        Refundment refundmentById = refundmentRepository.getRefundmentById(Integer.parseInt(rid));
        if (refundmentById != null) {
            //写入消息队列
            kafkaSender.send(refundmentById.toString());
            //更新数据库
            refundmentById.setStatus(RefundStatus.REFUNDING.getCode());
            refundmentRepository.save(refundmentById);
        }
        return "success";
    }

    @Override
    public List<Refundment> refund(Integer cid) {
        return refundmentRepository.getRefundmentByCid(cid);
    }
}
