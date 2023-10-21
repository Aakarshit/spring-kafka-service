package com.example.kafka_integration.kafka;

import com.example.kafka_integration.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaJsonProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    public KafkaJsonProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(User user){
        log.info("Message sent : {} ",user.toString());
        Message<User> message = MessageBuilder
                .withPayload(user)
                .setHeader(KafkaHeaders.TOPIC,"javaTestJson").build();
        kafkaTemplate.send(message);
    }
}
