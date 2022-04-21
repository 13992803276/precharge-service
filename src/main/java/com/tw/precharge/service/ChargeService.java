package com.tw.precharge.service;

import com.tw.precharge.constant.PayStatus;
import com.tw.precharge.constant.RefundStatus;
import com.tw.precharge.controller.dto.ChargeDTO;
import com.tw.precharge.controller.dto.RefundDTO;
import com.tw.precharge.controller.dto.ResultStatus;
import com.tw.precharge.controller.dto.WeChatPayResDTO;
import com.tw.precharge.controller.dto.WechatPayDTO;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.Refundment;
import com.tw.precharge.entity.RentUser;
import com.tw.precharge.infrastructure.mqService.kafka.KafkaSender;
import com.tw.precharge.infrastructure.repository.ChargementRepository;
import com.tw.precharge.infrastructure.repository.RefundmentRepository;
import com.tw.precharge.infrastructure.repository.UserRepository;
import com.tw.precharge.infrastructure.httpInterface.WechatPayClient;
import com.tw.precharge.util.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author lexu
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeService  {

    private final UserRepository userRepository;
    private final ChargementRepository chargementRepository;
    private final RefundmentRepository refundmentRepository;
    private final WechatPayClient wechatPayClient;
    private final KafkaSender kafkaSender;

    private static final Integer RETRY_TIMES = 3;

    public Chargement charge(ChargeDTO dto, String cid, Integer userId) {
        if (new BigDecimal(dto.getAmount()).compareTo(BigDecimal.valueOf(0.0)) < 0) {
            throw new BusinessException(ResultStatus.PARAM_ERROR);
        }
        //1 校验用户状态是否正确
        RentUser user = userRepository.getUserById(userId).orElse(null);
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
    @Transactional(rollbackOn = Exception.class)
    public String chargeConfirmation(String cid, String rid) {
        Chargement chargementById = chargementRepository.getChargementById(Integer.parseInt(rid));
        if (chargementById != null) {
            WeChatPayResDTO resDTO = WeChatPayResDTO.builder()
                    .wechatId(chargementById.getPayerId())
                    .amount(chargementById.getChargeAmount().toString())
                    .build();
            String codeTemp = cycleCharge(resDTO);
            if (ResultStatus.SUCCESS.getCode().equals(codeTemp)) {
                chargementById.setStatus(PayStatus.PAID.getCode());
                chargementRepository.save(chargementById);
                updateUserBalance(chargementById.getChargeAccount(),chargementById.getChargeAmount());
                return "success";
            }
        }
        return "failed";
    }

    private String cycleCharge(WeChatPayResDTO resDTO) {
        String codeTemp = "400";
        for (int i = 0; i < RETRY_TIMES; i++) {
            WechatPayDTO charge = wechatPayClient.payment(resDTO);
            if (ResultStatus.SUCCESS.getCode().equals(charge.getCode())) {
                codeTemp = "200";
                break;
            }
        }
        return codeTemp;
    }

    public List<Chargement> charge(Integer cid) {
        return chargementRepository.getChargementByCid(cid);
    }


    public Refundment refund(RefundDTO refundDTO, String cid, Integer userId) {
        if (new BigDecimal(refundDTO.getRefundAmount()).compareTo(BigDecimal.valueOf(0.0)) < 0) {
            throw new BusinessException(ResultStatus.PARAM_ERROR);
        }
        RentUser user = userRepository.getUserById(userId).orElse(null);
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

    public String refundConfirmation(String cid, String rid) {
        Refundment refundmentById = refundmentRepository.getRefundmentById(Integer.parseInt(rid));
        if (refundmentById != null) {
            WeChatPayResDTO weChatPayResDTO = WeChatPayResDTO.builder()
                    .wechatId(refundmentById.getRefundAccount())
                    .amount(refundmentById.getRefundAmount().toString())
                    .build();
            WechatPayDTO wechatRefund = wechatPayClient.refund(weChatPayResDTO);
            if (wechatRefund.getCode().equals(ResultStatus.SUCCESS.getCode())){
                //1.更新退款请求状态为"已退款"。
                refundmentById.setStatus(RefundStatus.REFUNDED.getCode());
                //2。更新账户余额
                this.updateUserBalance(refundmentById.getRefundAccount(), refundmentById.getRefundAmount());
            }else{
                //写入消息队列
                kafkaSender.send(refundmentById.toString());
                //更新退款状态数据库
                refundmentById.setStatus(RefundStatus.REFUNDING.getCode());
            }
            refundmentRepository.save(refundmentById);
        }
        return "success";
    }

    public List<Refundment> refund(Integer cid) {
        return refundmentRepository.getRefundmentByCid(cid);
    }

    private void updateUserBalance(String userAccount, BigDecimal amount) {
        RentUser user = userRepository.getRentUserByAccount(userAccount).get();
        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);
    }
}
