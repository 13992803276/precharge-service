package com.tw.precharge.controller.advice;

import com.tw.precharge.dto.RespondEntity;
import com.tw.precharge.dto.RespondStatus;
import com.tw.precharge.util.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author lexu
 */

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<RespondEntity<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return ResponseEntity.status(500).body(RespondEntity.error(RespondStatus.ERROR, errorMessage));
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<RespondEntity<String>> handleBusinessException(BusinessException exception) {
        log.info("system has a exception {}",exception.getMessage());
        RespondStatus status = exception.getRs();
        return ResponseEntity.status(Integer.parseInt(status.getCode())).body(RespondEntity.error(status));
    }
}
