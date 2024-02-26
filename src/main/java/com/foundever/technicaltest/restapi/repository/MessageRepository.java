package com.foundever.technicaltest.restapi.repository;

import com.foundever.technicaltest.restapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    //Optional<Message> findBySender(String sender);
    Optional<List<Message>> findBySender(String sender);
}
