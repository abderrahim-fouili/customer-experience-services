package com.foundever.technicaltest.restapi.service;

import com.foundever.technicaltest.restapi.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService extends CrudService<Long, Message> {
    //public Optional<Message> findMessageBySender(String sender);
    public Optional<List<Message>> findMessageBySender(String sender);

}
