package com.tw.precharge.util.exception;

import com.tw.precharge.dto.ResultStatus;

/**
 * @author lexu
 */
public class BusinessException extends RuntimeException{
    private final ResultStatus rs;

    public BusinessException(ResultStatus rs) {
        this.rs = rs;
    }

    public ResultStatus getRs() {
        return rs;
    }
}
