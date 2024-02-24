package com.foundever.technicaltest.restapi.service.imp;

import com.foundever.technicaltest.restapi.model.Message;
import com.foundever.technicaltest.restapi.repository.MessageRepository;
import com.foundever.technicaltest.restapi.service.MessageService;
import com.foundever.technicaltest.restapi.exception.BusinessException;
import com.foundever.technicaltest.restapi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImp implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Transactional(readOnly = true)
    public List<Message> findAll() {
        return this.messageRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Message findById(Long id) {
        return this.messageRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Message create(Message messageToCreate) {
        return this.messageRepository.save(messageToCreate);
    }

    public Message update(Long id, Message messageToUpdate) {
        Message message = this.findById(id);
        if (!message.getId().equals(messageToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }
        message.setSender(messageToUpdate.getSender());
        message.setContent(messageToUpdate.getContent());
        return this.messageRepository.save(message);
    }

    public void delete(Long id) {
        Message message = this.findById(id);
        this.messageRepository.delete(message);
    }
}
