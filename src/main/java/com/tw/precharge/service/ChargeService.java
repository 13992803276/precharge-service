package com.tw.precharge.service;

import com.tw.precharge.dto.ChargeDTO;
import com.tw.precharge.entity.Chargement;

import java.util.List;

/**
 * @author lexu
 */
public interface ChargeService {

    Chargement charge(ChargeDTO dto, String cid, Integer userId);

    String chargeConfirmation(String cid, String rid);

    List<Chargement> charge(Integer cid);
}
