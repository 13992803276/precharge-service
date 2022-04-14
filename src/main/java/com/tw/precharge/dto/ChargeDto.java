package com.tw.precharge.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lexu
 */
@Data
public class ChargeDto {
    private String weicharId;
    private BigDecimal amount;
}
