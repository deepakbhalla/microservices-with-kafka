package com.example.kafka.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer implements Serializable {

    private static final long serialVersionUID = -5752353070170724417L;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Topic
    String kafka_topic = "TestTopic";

    // Send message
    public void send(String message) {
        kafkaTemplate.send(kafka_topic, message);
    }
}
