package com.example.kafka_integration.controller;

import com.example.kafka_integration.dto.User;
import com.example.kafka_integration.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to the topic");
    }

    @PostMapping("/publish-json")
    public ResponseEntity<String> publishJsonMessage(@RequestBody User user){
        kafkaProducer.sendMessage(user);
        return ResponseEntity.ok("Message sent to the topic");
    }
}
