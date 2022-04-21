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
public class RespondEntity<T> implements Serializable {
    private String code;
    private String message;
    private T data;

    public static <T> RespondEntity<T> ok(T data) {
        return new RespondEntity<>(RespondStatus.SUCCESS.getCode(), RespondStatus.SUCCESS.getMessage(), data);
    }

    public static RespondEntity<String> error(RespondStatus status, String errMsg) {
        return new RespondEntity<>(status.getCode(), status.getMessage(), errMsg);
    }

    public static RespondEntity<String> error(RespondStatus status) {
        return new RespondEntity<>(status.getCode(), status.getMessage(), status.getMessage());
    }
}