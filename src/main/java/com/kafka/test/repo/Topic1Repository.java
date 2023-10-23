package com.kafka.test.repo;

import com.kafka.test.entity.Topic1Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Topic1Repository extends JpaRepository<Topic1Entity, Long> {
}
