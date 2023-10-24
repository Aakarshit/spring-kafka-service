package com.example.kafka_integration.kafka;

import com.example.kafka_integration.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    @Autowired
    @Qualifier("kafkaStringTemplate")
    private  KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    @Qualifier("kafkaJsonTemplate")
    private  KafkaTemplate<String, User> kafkaJsonTemplate;


    public void sendMessage(String message){
        log.info("Message sent {}",message);
        kafkaTemplate.send("topic-3",message);
    }

    public void sendMessage(User user){
        log.info("Message sent : {} ",user.toString());
        Message<User> message = MessageBuilder
                .withPayload(user)
                .setHeader(KafkaHeaders.TOPIC,"topic-4").build();
        kafkaJsonTemplate.send(message);
    }
}
