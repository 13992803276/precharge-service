package com.tw.precharge.controller;

import com.tw.precharge.dto.ChargeDto;
import com.tw.precharge.dto.Result;
import com.tw.precharge.entity.Chargement;
import com.tw.precharge.service.ChargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author lexu
 */
@Slf4j
@RestController
@RequestMapping( "/precharge_contract")
@Api(tags = "充值业务接口")
public class ChargeController {

    @Autowired
    ChargeService chargeService;

    @ApiOperation("创建充值请求接口")
    @PostMapping("/{cid}/charge")
    public Result<Chargement> charge(@RequestBody ChargeDto chargeDto, @PathVariable(name = "cid") String cid) {
        Integer userId = 1;
        return Result.ok(chargeService.charge(chargeDto,cid,userId));
    }

    @ApiOperation("创建充值确认接口")
    @PostMapping("/{cid}/charge/{rid}/confirmation/")
    public Result<ChargeDto> chargeConfirmation(@RequestBody ChargeDto chargeDto, @PathVariable String cid,
                                                @PathVariable String rid) {
        return Result.ok(chargeService.chargeConfirmation(chargeDto, cid, rid));
    }

    @ApiOperation(value = "充值请求查询接口", notes = "通过合同Id查询当前合同下的充值所有请求", httpMethod = "GET")
    @ApiImplicitParam(value = "cid:合同Id" ,required = true, dataType = "String" ,paramType = "query")
    @GetMapping("/precharge_contract/{cid}/charge")
    public Result<List<Chargement>> charge(@PathVariable String cid){
        return Result.ok(chargeService.charge(Integer.parseInt(cid)));
    }
}
