package com.example.kafka_integration.controller;

import com.example.kafka_integration.dto.User;
import com.example.kafka_integration.kafka.KafkaJsonProducer;
import com.example.kafka_integration.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    private final KafkaJsonProducer kafkaJsonProducer;

    public KafkaController(KafkaProducer kafkaProducer, KafkaJsonProducer kafkaJsonProducer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaJsonProducer = kafkaJsonProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to the topic");
    }

    @PostMapping("/publish-json")
    public ResponseEntity<String> publishJsonMessage(@RequestBody User user){
        kafkaJsonProducer.sendMessage(user);
        return ResponseEntity.ok("Message sent to the topic");
    }
}
