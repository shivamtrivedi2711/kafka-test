package com.kafka.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.test.entity.MergedEntity;
import com.kafka.test.entity.Topic1Entity;
import com.kafka.test.entity.Topic2Entity;
import com.kafka.test.repo.Topic1Repository;
import com.kafka.test.repo.Topic2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinService {

    @Autowired
    private Topic1Repository topic1EntityRepository;

    @Autowired
    private Topic2Repository topic2EntityRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void mergeAndSendToTopicC() throws JsonProcessingException {
        List<Topic1Entity> topic1Entities = topic1EntityRepository.findAll();
        List<Topic2Entity> topic2Entities = topic2EntityRepository.findAll();

        for (Topic1Entity topic1Entity : topic1Entities) {
            for (Topic2Entity topic2Entity : topic2Entities) {
                if (topic1Entity.getCountry().equals(topic2Entity.getCountry()) &&
                        topic1Entity.getCatalogNumber().equals(topic2Entity.getCatalogNumber())) {

                    MergedEntity mergedEntity = new MergedEntity();
                    // populate the mergedEntity fields from topic1Entity and topic2Entity
                    mergedEntity.setCatalogNumber(topic1Entity.getCatalogNumber());
                    mergedEntity.setCountry(topic1Entity.getCountry());
                    mergedEntity.setIsSelling(topic1Entity.getIsSelling());
                    mergedEntity.setModel(topic1Entity.getModel());
                    mergedEntity.setProductId(topic1Entity.getProductId());
                    mergedEntity.setRegistrationId(topic1Entity.getRegistrationId());
                    mergedEntity.setRegistrationNumber(topic1Entity.getRegistrationNumber());
                    mergedEntity.setSellingStatusDate(topic1Entity.getSellingStatusDate());

                    // Populating fields from Topic2Entity
                    mergedEntity.setOrderNumber(topic2Entity.getOrderNumber());
                    mergedEntity.setQuantity(topic2Entity.getQuantity());
                    mergedEntity.setSalesDate(topic2Entity.getSalesDate());

                    // Populating Audit fields (assuming you want the most recent event info, or you can adjust this logic accordingly)
                    mergedEntity.setEventName(topic2Entity.getAudit().getEventName()); // This assumes the audit is a nested object inside Topic2Entity with an `eventName` field.
                    mergedEntity.setSourceSystem(topic2Entity.getAudit().getSourceSystem()); // Similar assumption for `sourceSystem`.

                    try {
                        String jsonMergedEntity = objectMapper.writeValueAsString(mergedEntity);
                        kafkaTemplate.send("topic_c", jsonMergedEntity);
                    } catch (JsonProcessingException e) {
                        // Handle the exception (e.g., log it)
                    }
                }
            }
        }
    }
}

