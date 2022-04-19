package com.tw.precharge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author lexu
 */
@Getter
@AllArgsConstructor
public enum ResultStatus {
    SUCCESS("200", "success",200 ),
    PARAM_ERROR("400", "params is error", 400),
    USER_ERROR("405", "the user has been freeze", 405),
    ERROR("505", "error", HttpStatus.INTERNAL_SERVER_ERROR.value());

    private final String code;
    private final String message;
    private final int httpCode;
}

