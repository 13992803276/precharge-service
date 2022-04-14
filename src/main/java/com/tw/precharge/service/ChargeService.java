package com.tw.precharge.service;

import com.tw.precharge.dto.ChargeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lexu
 */
@Service
@Slf4j
public class ChargeService {
    public void charge(ChargeDto dto){
        log.info(dto.toString());
    }
}
