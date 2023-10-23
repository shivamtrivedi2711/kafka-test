package com.kafka.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.test.entity.Topic1Entity;
import com.kafka.test.entity.Topic2Entity;
import com.kafka.test.repo.Topic1Repository;
import com.kafka.test.repo.Topic2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class KafkaListnerService {
    @Autowired
    private Topic1Repository topic1Repository;

    @Autowired
    private Topic2Repository topic2Repository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "topic_a",groupId = "test")
    public void consumeTopic1(String message) throws JsonProcessingException {

        Topic1Entity entity = objectMapper.readValue(message, Topic1Entity.class);

        if (!"001".equals(entity.getCountry())) {
            return;  // Skip processing for this message
        }
        if (entity.getCatalogNumber().length() != 5) {
            return;  // Skip processing for this message
        }
        String dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
                .withZone(ZoneId.of("UTC"))
                .format(entity.getSellingStatusDate());
        if (entity.getSellingStatusDate() != null &&
                !isValidDateFormat(dateStr)) {
            return;  // Skip processing for this message
        }

        // Validate sales_date format

        topic1Repository.save(entity);
    }

    @KafkaListener(topics = "topic_b",groupId = "test")
    public void consumeTopic2(String message) throws JsonProcessingException {
        Topic2Entity entity = objectMapper.readValue(message, Topic2Entity.class);
        if (!"001".equals(entity.getCountry())) {
            return;  // Skip processing for this message
        }
        if (entity.getCatalogNumber().length() != 5) {
            return;  // Skip processing for this message
        }
        if (entity.getSalesDate() != null &&
                !isValidDateFormat(entity.getSalesDate())) {
            return;  // Skip processing for this message
        }
        System.out.println(entity);
        topic2Repository.save(entity);
    }

    @KafkaListener(topics = "topic_c",groupId = "test")
    public void consumeTopic3(String message)  {

        System.out.println("Message recieved in c");
        System.out.println(message);
    }

    public boolean isValidDateFormat(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public boolean isValidDateFormat(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        try {
            String formattedDate = dateTime.format(formatter);
            // Attempt to parse the string back to LocalDateTime to verify the format
            LocalDateTime.parse(formattedDate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
