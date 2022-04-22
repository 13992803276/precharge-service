package com.tw.precharge.controller;

import com.tw.precharge.controller.dto.ChargeDTO;
import com.tw.precharge.controller.dto.RefundDTO;
import com.tw.precharge.controller.dto.RespondEntity;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.entity.Refundment;
import com.tw.precharge.service.ChargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lexu
 */
@Slf4j
@RestController
@RequestMapping( "/precharge_contract")
@Api(tags = "预充值合约业务接口")
public class ChargeController {

    @Autowired
    ChargeService chargeService;

    @ApiOperation("创建充值请求接口")
    @PostMapping("/{cid}/charge")
    public RespondEntity<Chargement> charge(@RequestBody ChargeDTO chargeDto, @PathVariable(name = "cid") String cid) {
        Integer userId = 1;
        return RespondEntity.ok(chargeService.charge(chargeDto,cid,userId));
    }

    @ApiOperation("创建充值确认接口")
    @PostMapping("/{cid}/charge/{rid}/confirmation")
    public RespondEntity<String> chargeConfirmation(@PathVariable String cid, @PathVariable String rid) {
        return RespondEntity.ok(chargeService.chargeConfirmation(cid, rid));
    }

    @ApiOperation(value = "充值请求查询接口", notes = "通过合同Id查询当前合同下的充值所有请求", httpMethod = "GET")
    @ApiImplicitParam(value = "cid:合同Id" ,required = true, dataType = "String" ,paramType = "query")
    @GetMapping("/{cid}/charge")
    public RespondEntity<List<Chargement>> charge(@PathVariable String cid){
        return RespondEntity.ok(chargeService.charge(Integer.parseInt(cid)));
    }

    @ApiOperation("创建退款请求接口")
    @PostMapping("/{cid}/refund")
    public RespondEntity<Refundment> refund(@RequestBody RefundDTO refundDTO, @PathVariable(name = "cid") String cid) {
        Integer userId = 1;
        return RespondEntity.ok(chargeService.refund(refundDTO,cid,userId));
    }

    @ApiOperation("创建退款确认接口")
    @PostMapping("/{cid}/refund/{rid}/confirmation")
    public RespondEntity<String> refundConfirmation(@PathVariable String cid, @PathVariable String rid) {
        return RespondEntity.ok(chargeService.refundConfirmation(cid, rid));
    }
    @ApiOperation(value = "充值请求查询接口", notes = "通过合同Id查询当前合同下的充值所有请求", httpMethod = "GET")
    @ApiImplicitParam(value = "cid:合同Id" ,required = true, dataType = "String" ,paramType = "query")
    @GetMapping("/{cid}/refund")
    public RespondEntity<List<Refundment>> refund(@PathVariable String cid){
        return RespondEntity.ok(chargeService.refund(Integer.parseInt(cid)));
    }

}
