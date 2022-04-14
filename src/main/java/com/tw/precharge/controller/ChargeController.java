package com.tw.precharge.controller;

import com.tw.precharge.dto.ChargeDto;
import com.tw.precharge.service.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author lexu
 */
@Slf4j
@RestController
@RequestMapping(value = "/precharge_contract")
public class ChargeController {

    @Autowired
    ChargeService chargeService;

    @PostMapping("/{cid}/charge")
    public String charge(HttpServletResponse response, @RequestParam String weicharId,
                       @RequestParam BigDecimal amount, @PathVariable(name = "cid") String cid) {
        chargeService.charge(ChargeDto.builder().cid(Integer.parseInt(cid))
                .weicharId(weicharId)
                .amount(amount)
                .build());
        return "success";
    }
}
