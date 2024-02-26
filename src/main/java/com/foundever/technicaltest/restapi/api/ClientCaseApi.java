package com.foundever.technicaltest.restapi.api;


import com.foundever.technicaltest.restapi.model.ClientCase;
import com.foundever.technicaltest.restapi.model.Message;
import com.foundever.technicaltest.restapi.service.ClientCaseService;
import com.foundever.technicaltest.restapi.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.foundever.technicaltest.restapi.util.constants.GlobalConstants.*;

@RestController
@RequestMapping(BASE_PATH+"client-cases")
@Tag(name = CLIENTCASE_APIS_TAG, description = INFO_API_DESCRIPTION_CLIENT_CASES)
@Slf4j

public record ClientCaseApi (ClientCaseService clientCaseService, MessageService messageService) {

    /**
     * Get client case by ID.
     *
     * @param clientCaseId ID of the client case to retrieve.
     * @return ResponseEntity containing the retrieved ClientCases.
     */
    @GetMapping("/{clientCaseId}")
    @Operation(summary = "Get client case by ID", description = "Get a specific client case based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "ClientCase not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ClientCase> findClientCaseById(@PathVariable Long clientCaseId) {
        log.info("Start api: Getting the client case with ID '{}'",clientCaseId);
        ClientCase clientCase = clientCaseService.findById(clientCaseId);
        log.info("End api: Getting the client case with ID '{}'",clientCaseId);
        return ResponseEntity.ok(clientCase);
    }

    /**
     * Get all client cases.
     *
     * @return ResponseEntity containing a list of all registered client cases.
     */
    @GetMapping
    @Operation(summary = "Get all client case", description = "Get a list of all registered client case")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "ClientCases not found", content = @Content)})
    public ResponseEntity<List<ClientCase>> getAllClientCases() {
        log.info("Start api: Getting a list of all registered client case");
        List<ClientCase> clientCase = clientCaseService.findAll();
        log.info("End api: Getting a list of all registered client case");
        return ResponseEntity.ok(clientCase);

    }

    /**
     * Add a new client case.
     *
     * @param param_clientCase The ClientCase to be added.
     * @return ResponseEntity containing the created ClientCase.
     */
    @PostMapping(path = "/add-client-case",consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Add client case",description = "Create a new client case and returns the created client case")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ClientCase created successfully!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid client case data provided", content = @Content)})
    public ResponseEntity<ClientCase> addNewClientCase(@RequestBody ClientCase param_clientCase) {
        // TODO: Create a DTO to avoid expose unnecessary ClientCase model attributes.
        log.info("Start api: Adding new client case '{}'", param_clientCase);
        ClientCase createdClientCase = clientCaseService.create(param_clientCase);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdClientCase.getClientCaseId())
                .toUri();
        log.info("End api: Adding new client case");
        return ResponseEntity.created(location).body(createdClientCase);
    }

    /**
     * Update the client reference of an existing client case.
     *
     * @param clientCaseId      ID of the client case to be updated.
     * @param param_clientCase  The ClientCase object containing the updated client reference.
     * @return ResponseEntity containing the updated ClientCase.
     */
    @PatchMapping("/update-client-case/{clientCaseId}")
    @Operation(summary = "Update the client reference", description = "Update client reference of an existing client case based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "client case updated successfully"),
            @ApiResponse(responseCode = "404", description = "client case not found"),
            @ApiResponse(responseCode = "422", description = "Invalid client case data provided")
    })
    public ResponseEntity<ClientCase> updateClientReference(@PathVariable Long clientCaseId, @RequestBody ClientCase param_clientCase) {
        // TODO: Create a DTO to avoid expose unnecessary ClientCase model attributes.
        log.info("Start api: Updating client reference with ID '{}'",clientCaseId);
        ClientCase udpatedClientCase = clientCaseService.updateClientCaseCR(clientCaseId, param_clientCase.getClientReference());
        log.info("End api: Updating client reference with ID '{}'",clientCaseId);
        return ResponseEntity.ok(udpatedClientCase);
    }

    /**
     * Add a message to an existing client case.
     *
     * @param clientCaseId ID of the client case to which the message will be added.
     * @param messageId    ID of the message to be added.
     * @return ResponseEntity containing the updated Message.
     */
    @PatchMapping("/{clientCaseId}/add-message/{messageId}")
    @Operation(summary = "Add message to client case", description = "Add message to Client Case to an existing client case based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client Case updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client Case not found"),
    })
    public ResponseEntity<Message> addMessageAssistantToClientCase(
            @PathVariable Long clientCaseId, @PathVariable Long messageId) {
        log.info("Start api: Adding message ID '{}' to Client Case ID '{}'",messageId,clientCaseId);
        Message udpatedMessage = clientCaseService.addMessageAssistantToClientCase(clientCaseId, messageId);
        log.info("End api: Adding message ID '{}' to Client Case ID '{}'",messageId,clientCaseId);
        return ResponseEntity.ok(udpatedMessage);
    }

    /**
     * Delete an existing client case.
     *
     * @param clientCaseId ID of the client case to be deleted.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @DeleteMapping("/delete-client-case/{clientCaseId}")
    @Operation(summary = "Delete a client case", description = "Delete an existing client case based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "client case deleted successfully"),
            @ApiResponse(responseCode = "404", description = "client case not found")
    })
    public ResponseEntity<Void> deleteClientCase(@PathVariable Long clientCaseId) {
        log.info("Start api: Delete an existing client case with ID '{}'",clientCaseId);
        clientCaseService.delete(clientCaseId);
        log.info("End api: Delete an existing client case with ID '{}'",clientCaseId);
        return ResponseEntity.noContent().build();
    }
}
