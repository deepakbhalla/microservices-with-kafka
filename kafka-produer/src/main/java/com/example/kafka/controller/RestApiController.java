package com.example.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.service.KafkaProducer;

@RestController
@RequestMapping(value = "/kafka/api/v1")
public class RestApiController {

    @Autowired
    KafkaProducer producer;

    @GetMapping(value = "/produce")
    public String publish(@RequestParam(value = "message") String message) {
        producer.send(message);
        return "Message has been sent successfully to Kafka server";
    }
}
