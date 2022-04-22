package com.tw.precharge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lexu
 */
@Getter
@AllArgsConstructor
public enum RespondStatus {
    SUCCESS("200", "success"),
    PARAM_ERROR("400", "params is error"),
    USER_ERROR("405", "the user has been freeze"),
    ERROR("505", "error");

    private final String code;
    private final String message;
}

