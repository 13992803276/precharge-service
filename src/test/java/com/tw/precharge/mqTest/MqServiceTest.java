package com.tw.precharge.mqTest;

import com.tw.precharge.infrastructure.mqService.kafka.KafkaSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MqServiceTest {

    @InjectMocks
    KafkaSender kafkaSender;

    @Mock
    private  KafkaTemplate<String, String> kafkaTemplate;

    @Captor
    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void test_send_message_prams(){
        kafkaSender.send("a refund message is send successful");
        verify(kafkaTemplate,times(1)).send(any(),argumentCaptor.capture());
        String value = argumentCaptor.getValue();
        Assertions.assertTrue(value.contains("a refund message is send successful"));
    }

    @Test
    public void test_send_message_to_mq_should_return_message(){
        Mockito.when(kafkaTemplate.send(anyString(),any())).thenReturn(null);
        String message = kafkaSender.send("message");
        Assertions.assertEquals(message,"message");
    }
}
