package com.tw.precharge.constant;

import com.tw.precharge.service.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author lexu
 */
@Slf4j
@RestController
@RequestMapping(value = "/precharge_contract/{cid}/charge")
public class ChargeController {
    @Autowired
    ChargeService chargeService;
    public void charge(HttpServletResponse response, @RequestParam String weicharId,
                       @RequestParam BigDecimal amount,@PathVariable(name = "cid") Optional<String> cid){


    }
}
