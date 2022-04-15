package com.tw.precharge.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author lexu
 */
@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String account;
    private String phone;
    private String address;
    private BigDecimal  balance;
    private LocalDate created;
    private String status;
}
