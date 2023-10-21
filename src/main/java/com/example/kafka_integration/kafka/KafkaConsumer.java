package com.example.kafka_integration.kafka;

import com.example.kafka_integration.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "javaTest", groupId = "my-group")
    public void consume(String message){
        log.info("message received, {}",message);
    }

    @KafkaListener(topics = "javaTestJson", groupId = "my-group")
    public void consumeJson(User user){
        log.info("message received, {}",user.toString());
    }
}
