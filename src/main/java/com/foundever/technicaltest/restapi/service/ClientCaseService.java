package com.foundever.technicaltest.restapi.service;

import com.foundever.technicaltest.restapi.model.ClientCase;
import com.foundever.technicaltest.restapi.model.Message;

public interface ClientCaseService extends CrudService<Long, ClientCase> {
    /**
     * Add a message's client to ClientCase.
     *
     * @param clientCaseId ID of the target ClientCase.
     * @param sender       Sender of the message.
     * @return Message entity representing the added message.
     */
    public Message addMessageClientToClientCase(Long clientCaseId, String sender);

    /**
     * link an assistant's message to a specific ClientCase.
     *
     * @param clientCaseId ID of the target ClientCase.
     * @param messageId    ID of the message from the assistant.
     * @return Message entity representing the added message.
     */
    public Message addMessageAssistantToClientCase(Long clientCaseId, Long messageId);

    /**
     * Update the client reference of a specific ClientCase.
     *
     * @param id             ID of the ClientCase to be updated.
     * @param clientReference New client reference value.
     * @return Updated ClientCase entity.
     */
    public ClientCase updateClientCaseCR(Long id,  String clientReference);
}
