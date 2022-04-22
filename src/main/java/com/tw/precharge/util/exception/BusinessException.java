package com.tw.precharge.util.exception;

import com.tw.precharge.controller.dto.RespondStatus;

/**
 * @author lexu
 */
public class BusinessException extends RuntimeException{
    private final RespondStatus rs;

    public BusinessException(RespondStatus rs) {
        this.rs = rs;
    }

    public RespondStatus getRs() {
        return rs;
    }
}
