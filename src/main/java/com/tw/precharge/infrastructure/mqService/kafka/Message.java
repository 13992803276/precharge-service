package com.tw.precharge.infrastructure.mqService.kafka;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author lexu
 */
@Data
public class Message {
    private Long id;
    private String msg;
    private LocalDate  sendTime;
}
