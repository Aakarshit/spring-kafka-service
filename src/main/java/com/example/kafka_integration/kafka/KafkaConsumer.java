package com.example.kafka_integration.kafka;

import com.example.kafka_integration.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "topic-3", groupId = "my-group-1")
    public void consume(String message){
        log.info("message received, {}",message);
    }

    @KafkaListener(topics = "topic-4", groupId = "my-group-2",containerFactory = "userKafkaListenerContainerFactory")
    public void consumeJson(User user){
        log.info("message received, {}",user.toString());
    }
}
