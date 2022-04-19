package com.tw.precharge.controller.advice;

import com.tw.precharge.controller.dto.Result;
import com.tw.precharge.controller.dto.ResultStatus;
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
    public ResponseEntity<Result<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return ResponseEntity.status(ResultStatus.ERROR.getHttpCode()).body(Result.error(ResultStatus.ERROR, errorMessage));
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Result<String>> handleBusinessException(BusinessException exception) {
        log.info("system has a exception {}",exception.getMessage());
        ResultStatus status = exception.getRs();
        return ResponseEntity.status(status.getHttpCode()).body(Result.error(status));
    }
}
