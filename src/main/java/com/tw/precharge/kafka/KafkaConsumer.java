package com.tw.precharge.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author lexu
 */
@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = {"refund"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional.ofNullable(record.value())
                .ifPresent(message -> {
                    log.info("【+++++++++++++++++ record = {} 】", record);
                    log.info("【+++++++++++++++++ message = {}】", message);
                   //解析退款申请Id并更新退款状态
                });
    }

}
