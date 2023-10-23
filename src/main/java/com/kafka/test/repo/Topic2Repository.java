package com.kafka.test.repo;

import com.kafka.test.entity.Topic1Entity;
import com.kafka.test.entity.Topic2Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Topic2Repository extends JpaRepository<Topic2Entity, Long> {
}
