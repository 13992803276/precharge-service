package com.tw.precharge.service;

import com.tw.precharge.dto.ChargeDto;
import com.tw.precharge.dto.ResultStatus;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.User;
import com.tw.precharge.repository.UserRepository;
import com.tw.precharge.util.UserUtil;
import com.tw.precharge.util.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * @author lexu
 */
@Service
@Slf4j
public class ChargeService {

    @Autowired
    UserRepository userRepository;

    public String charge(ChargeDto dto, String cid, Integer userId) {
        //1 校验用户状态是否正确
        User user = UserUtil.getUserById(userId).orElse(null);
        //User user = userRepository.getById(userId);
        if (user != null) {
            // 生成支付请求
            Chargement chargement = Chargement.builder()
                    .id(1)
                    .cid(Integer.parseInt(cid))
                    .title("思沃租房平台账户预充值")
                    .payLimit(0.5f)
                    .chargeAmount(new BigDecimal(dto.getAmount()))
                    .payerName(user.getName())
                    .payerId(user.getAccount())
                    .chargeType("weichart")
                    .chargeAccount(dto.getWeicharId())
                    .payeeName("思沃租房平台")
                    .payeeId("thoughtworks")
                    .createdBy("思沃租房平台")
                    .createdDate(LocalDate.now()).build();
            //保存数据库
            return chargement.getObject();
        } else {
            throw new BusinessException(ResultStatus.USER_ERROR);
        }
    }

    public ChargeDto chargeConfirmation(ChargeDto chargeDto, String cid, String rid) {
        //校验参数
        log.info("ChargeDto = {} , cid = {} , rid = {}", chargeDto, cid, rid);
        return chargeDto;
    }
}
