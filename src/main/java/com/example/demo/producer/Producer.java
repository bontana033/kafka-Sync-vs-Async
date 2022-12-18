package com.example.demo.producer;

import com.example.demo.domain.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Producer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendEventAsynchronous(Event event) throws JsonProcessingException {
        Integer key = event.getId();
        String value = objectMapper.writeValueAsString(event);
        // asynchronous
        kafkaTemplate.sendDefault(key, value).whenComplete((sendResult, ex)->{
                   if(ex != null)   handleFailure(key, value, ex);
                   else handleSuccess(key, value, sendResult);
                });
    }

    public SendResult<Integer, String> sendEventSynchronous(Event event) throws JsonProcessingException {
        Integer key = event.getId();
        String value = objectMapper.writeValueAsString(event);
        SendResult<Integer, String> sendResult = null;
        // synchronous
        try {
            sendResult = kafkaTemplate.sendDefault(key, value).get();
            handleSuccess(key, value, sendResult);
        } catch (Exception e) {
            handleFailure(key, value, e);
        }
        return sendResult;
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
//        log.info("Message Sent SuccessFully for the key : {} and the value is {}, partition is {}", key, value, result.getRecordMetadata().partition());
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }
}
