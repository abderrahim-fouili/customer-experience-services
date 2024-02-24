package com.foundever.technicaltest.restapi.repository;

import com.foundever.technicaltest.restapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
