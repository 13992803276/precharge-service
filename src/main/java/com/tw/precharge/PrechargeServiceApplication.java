package com.tw.precharge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lexu
 */
@SpringBootApplication
@EnableFeignClients
public class PrechargeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrechargeServiceApplication.class, args);
    }

}
