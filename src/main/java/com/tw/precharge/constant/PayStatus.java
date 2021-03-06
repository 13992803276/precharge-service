package com.tw.precharge.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lexu
 */
@Getter
@AllArgsConstructor
public enum PayStatus {
    STAY_PAY("0"), PAYING("1"), PAID("2");
    private final String code;

}
