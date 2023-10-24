package com.example.kafka_integration.config;

import com.example.kafka_integration.dto.User;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    String broker;
    @Bean
    public Map<String, Object> stringKafkaProducer(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,broker);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return properties;
    }

    @Bean
    public ProducerFactory<String,User> userProducerFactory(){
        return new DefaultKafkaProducerFactory<>(userKafkaProducer());
    }
    @Bean
    public ProducerFactory<String,String> stringProducerFactory(){
        return new DefaultKafkaProducerFactory<>(stringKafkaProducer());
    }

    @Bean(name="kafkaJsonTemplate")
    public KafkaTemplate<String,User> userKafkaTemplate(){
        return new KafkaTemplate<>(userProducerFactory());
    }
    @Bean(name="kafkaStringTemplate")
    public KafkaTemplate<String,String> stringKafkaTemplate(){
        return new KafkaTemplate<>(stringProducerFactory());
    }

    @Bean
    public Map<String, Object> userKafkaProducer(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,broker);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);
        return properties;
    }
}
