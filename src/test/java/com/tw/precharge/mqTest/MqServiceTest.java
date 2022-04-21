package com.tw.precharge.mqTest;

import com.tw.precharge.infrastructure.mqService.kafka.KafkaSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MqServiceTest {

    @Mock
    KafkaSender kafkaSender = Mockito.mock(KafkaSender.class);


    @Captor
    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void test_send_message_prams(){
        kafkaSender.send("a refund message is send successful");
        verify(kafkaSender,times(1)).send(argumentCaptor.capture());
        Assertions.assertEquals(argumentCaptor.getValue(),"a refund message is send successful");
    }

    @Test
    public void test_send_message_to_mq_should_return_message(){
        Mockito.when(kafkaSender.send(anyString())).thenReturn("a refund message is send successful");
        String message = kafkaSender.send("message");
        Assertions.assertEquals(message,"a refund message is send successful");
    }
}
