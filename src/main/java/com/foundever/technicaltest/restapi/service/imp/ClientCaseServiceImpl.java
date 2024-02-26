package com.foundever.technicaltest.restapi.service.imp;


import com.foundever.technicaltest.restapi.exception.BusinessException;
import com.foundever.technicaltest.restapi.exception.NotFoundException;
import com.foundever.technicaltest.restapi.model.ClientCase;
import com.foundever.technicaltest.restapi.model.Message;
import com.foundever.technicaltest.restapi.repository.ClientCaseRepository;
import com.foundever.technicaltest.restapi.repository.MessageRepository;
import com.foundever.technicaltest.restapi.service.ClientCaseService;
import com.foundever.technicaltest.restapi.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class ClientCaseServiceImpl implements ClientCaseService {
    @Autowired
    private ClientCaseRepository clientCaseRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;

    /**
     * Retrieve all client cases.
     *
     * @return List of all ClientCase entities.
     */
    @Transactional
    public List<ClientCase> findAll() {
        return this.clientCaseRepository.findAll();
    }

    /**
     * Retrieve a specific client case by its ID.
     *
     * @param id ID of the target ClientCase.
     * @return ClientCase entity corresponding to the provided ID.
     * @throws NotFoundException if no ClientCase is found with the given ID.
     */
    @Transactional(readOnly = true)
    public ClientCase findById(Long id) {
        return this.clientCaseRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Create a new client case.
     *
     * @param clientCaseToCreate ClientCase object representing the new client case.
     * @return Newly created ClientCase entity.
     */
    public ClientCase create(ClientCase clientCaseToCreate) {
        ClientCase clientCase;
        clientCase=clientCaseRepository.save(clientCaseToCreate);
        addMessageClientToClientCase(clientCase.getClientCaseId(),clientCase.getName());
        return clientCase;
    }

    /**
     * Update a client case (Not implemented in this class).
     *
     * @param aLong  ID of the target ClientCase.
     * @param entity ClientCase entity containing the updated data.
     * @return Updated ClientCase entity.
     */
    @Override
    public ClientCase update(Long aLong, ClientCase entity) {
        return null;
    }

    /**
     * Add a message's client to a ClientCase.
     *
     * @param clientCaseId ID of the target ClientCase.
     * @param sender       Sender of the message.
     * @return Message entity representing the added message.
     * @throws BusinessException if multiple messages are found with the same client name.
     * @throws BusinessException if no message is found with the same client name.
     */
    public Message addMessageClientToClientCase(Long clientCaseId, String sender) {
        ClientCase clientCase = findById(clientCaseId);
        Optional<List<Message>> message = messageService.findMessageBySender(sender);
        if(message.isPresent()){
           if (message.get().size() == 1){
            message.get().get(0).setClientCase(clientCase);
            return messageRepository.save(message.get().get(0));
        }
           else throw new BusinessException("There are several messages found with the same client name.");
        }
        else {
         throw new BusinessException("No message found with the same client name.");
        }
    }

    /**
     * Add an assistant's message to a specific ClientCase.
     *
     * @param clientCaseId ID of the target ClientCase.
     * @param messageId    ID of the message from the assistant.
     * @return Message entity representing the added message.
     */
    public Message addMessageAssistantToClientCase(Long clientCaseId, Long messageId) {
        Message message = messageService.findById(messageId);
        message.setClientCase(this.findById(clientCaseId));
        return messageRepository.save(message);
    }

    /**
     * Update the client reference of a specific ClientCase.
     *
     * @param id             ID of the ClientCase to be updated.
     * @param clientReference New client reference value.
     * @return Updated ClientCase entity.
     * @throws BusinessException if the update ID is not the same as the ClientCase ID.
     */
    public ClientCase updateClientCaseCR(Long id,  String clientReference) {
        ClientCase clientCase = this.findById(id);
        if (!clientCase.getClientCaseId().equals(id)) {
            throw new BusinessException("Update ID must be the same.");
        }
        clientCase.setClientReference(clientReference);
        return this.clientCaseRepository.save(clientCase);
    }

    /**
     * Delete a client case by its ID.
     *
     * @param id ID of the ClientCase to be deleted.
     */
    public void delete(Long id) {
        ClientCase clientCase = this.findById(id);
        this.clientCaseRepository.delete(clientCase);
    }
}