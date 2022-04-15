package com.tw.precharge.controller;

import com.tw.precharge.dto.ChargeDto;
import com.tw.precharge.dto.Result;
import com.tw.precharge.service.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lexu
 */
@Slf4j
@RestController
@RequestMapping( "/precharge_contract")
public class ChargeController {

    @Autowired
    ChargeService chargeService;

    @PostMapping("/{cid}/charge")
    public Result<String> charge(@RequestBody ChargeDto chargeDto, @PathVariable(name = "cid") String cid) {
        Integer userId = 1;
        return Result.ok(chargeService.charge(chargeDto,cid,userId));
    }

    @PostMapping("/{cid}/charge/{rid}/confirmation/")
    public Result<ChargeDto> chargeConfirmation(@RequestBody ChargeDto chargeDto, @PathVariable String cid,
                                                @PathVariable String rid) {
        return Result.ok(chargeService.chargeConfirmation(chargeDto));
    }
}
