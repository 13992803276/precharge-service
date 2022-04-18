package com.tw.precharge.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lexu
 */
@AllArgsConstructor
@Getter
public enum RefundStatus {
    STAY_REFUND("0"), REFUNDING("1"), REFUNDED("2");
    private final String code;
}
