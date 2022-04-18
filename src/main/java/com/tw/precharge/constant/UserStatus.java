package com.tw.precharge.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lexu
 */
@Getter
@AllArgsConstructor
public  enum UserStatus {
    NORMAL("0"), FREEZE("1");
    private final String code;

    public String getCode() {
        return code;
    }
}
