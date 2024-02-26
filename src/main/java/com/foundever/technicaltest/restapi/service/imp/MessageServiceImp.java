package com.foundever.technicaltest.restapi.service.imp;

import com.foundever.technicaltest.restapi.model.ClientCase;
import com.foundever.technicaltest.restapi.model.Message;
import com.foundever.technicaltest.restapi.repository.ClientCaseRepository;
import com.foundever.technicaltest.restapi.repository.MessageRepository;
import com.foundever.technicaltest.restapi.service.MessageService;
import com.foundever.technicaltest.restapi.exception.BusinessException;
import com.foundever.technicaltest.restapi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageServiceImp implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ClientCaseRepository clientCaseRepository;

    /**
     * Retrieve all messages.
     *
     * @return List of all Messages.
     */
    @Transactional(readOnly = true)
    public List<Message> findAll() {
        return this.messageRepository.findAll();
    }

    /**
     * Retrieve a specific message by its ID.
     *
     * @param id ID of the target Message.
     * @return Message entity corresponding to the provided ID.
     * @throws NotFoundException if no Message is found with the given ID.
     */
    @Transactional(readOnly = true)
    public Message findById(Long id) {

        return this.messageRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Create a new message.
     *
     * @param messageToCreate Message object representing the new message.
     * @return Newly created Message entity.
     */
    public Message create(Message messageToCreate) {

        return this.messageRepository.save(messageToCreate);
    }

    /**
     * Update a message.
     *
     * @param id              ID of the target Message.
     * @param messageToUpdate Message entity containing the updated data.
     * @return Updated Message entity.
     * @throws BusinessException if the update ID is not the same as the Message ID.
     */
    public Message update(Long id, Message messageToUpdate) {
        Message message = this.findById(id);
        if (!message.getMessageId().equals(messageToUpdate.getMessageId())) {
            throw new BusinessException("Update ID must be the same.");
        }
        message.setSender(messageToUpdate.getSender());
        message.setContent(messageToUpdate.getContent());
        return this.messageRepository.save(message);
    }

    /**
     * Delete a message by its ID.
     *
     * @param id ID of the Message to be deleted.
     */
    public void delete(Long id) {
        Message message = this.findById(id);
        this.messageRepository.delete(message);
    }

    /**
     * Find messages by sender.
     *
     * @param sender Sender of the messages to be retrieved.
     * @return Optional list of Message entities with the provided sender.
     */
    public Optional<List<Message>> findMessageBySender(String sender) {

        return messageRepository.findBySender(sender);
    }
}
