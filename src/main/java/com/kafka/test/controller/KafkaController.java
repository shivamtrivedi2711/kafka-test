package com.kafka.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.test.service.JoinService;
import com.kafka.test.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private JoinService joinService;

    @PostMapping("/send/topic_a")
    public ResponseEntity<String> sendMessageA(@RequestBody String message) {
        producerService.sendMessage(message,"topic_a");
        return ResponseEntity.ok("Message sent");
    }
    @PostMapping("/send/topic_b")
    public ResponseEntity<String> sendMessageB(@RequestBody String message) {
        producerService.sendMessage(message,"topic_b");
        return ResponseEntity.ok("Message sent");
    }

    @PutMapping("/merge")
    public ResponseEntity<String> merge() throws JsonProcessingException {
        joinService.mergeAndSendToTopicC();
        return ResponseEntity.ok("Messages Merged");
    }
}
