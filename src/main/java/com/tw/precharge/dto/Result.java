package com.tw.precharge.dto;

/**
 * @author lexu
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private String code;
    private String message;
    private T data;

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage(), data);
    }

    public static Result<String> error(ResultStatus status, String errMsg) {
        return new Result<>(status.getCode(), status.getMessage(), errMsg);
    }

    public static Result<String> error(ResultStatus status) {
        return new Result<>(status.getCode(), status.getMessage(), status.getMessage());
    }
}