package com.tw.precharge.service;

import com.tw.precharge.controller.dto.ChargeDTO;
import com.tw.precharge.controller.dto.RefundDTO;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.Refundment;

import java.util.List;

/**
 * @author lexu
 */
public interface ChargeService {

    Chargement charge(ChargeDTO dto, String cid, Integer userId);

    String chargeConfirmation(String cid, String rid);

    List<Chargement> charge(Integer cid);

    Refundment refund(RefundDTO refundDTO, String cid, Integer userId);

    String refundConfirmation(String cid, String rid);

    List<Refundment> refund(Integer cid);
}
