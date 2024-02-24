package com.foundever.technicaltest.restapi.api;


import com.foundever.technicaltest.restapi.model.Message;
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

import static com.foundever.technicaltest.restapi.util.constants.GlobalConstants.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(BASE_PATH+"messages")
@Tag(name = MESSAGE_APIS_TAG, description = INFO_API_DESCRIPTION)
@Slf4j

public record MessageApi(MessageService messageService) {

    @GetMapping("/{id}")
    @Operation(summary = "Get message by ID", description = "Get a specific message based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Message not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        log.info("Start api: Getting the message with ID '{}'",id);
        Message message = messageService.findById(id);
        log.info("End api: Getting the message with ID '{}'",id);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    @Operation(summary = "Get all messages", description = "Get a list of all registered messages")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation!"),
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
    @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content)})
    public ResponseEntity<List<Message>> getAllMessages() {
        log.info("Start api: Getting a list of all registered messages");
        List<Message> messages = messageService.findAll();
        log.info("End api: Getting a list of all registered messages");
        return ResponseEntity.ok(messages);

    }

    @PostMapping(path = "/add", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Add message",description = "Create a new message and returns the created message")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Message created successfully!", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
    @ApiResponse(responseCode = "422", description = "Invalid message data provided", content = @Content)})
    public ResponseEntity<Message> addNewMessage(@RequestBody Message param_message) {
        // TODO: Create a DTO to avoid expose unnecessary Message model attributes.
        log.info("Start api: Adding new message '{}'", param_message);
        Message createdMessage = messageService.create(param_message);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMessage.getId())
                .toUri();
        log.info("End api: Adding new message");
        return ResponseEntity.created(location).body(createdMessage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a message", description = "Update an existing message based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "message updated successfully"),
            @ApiResponse(responseCode = "404", description = "message not found"),
            @ApiResponse(responseCode = "422", description = "Invalid message data provided")
    })
    public ResponseEntity<Message> update(@PathVariable Long id, @RequestBody Message param_message) {
        // TODO: Create a DTO to avoid expose unnecessary Message model attributes.
        log.info("Start api: Updating an existing message with ID '{}'",id);
        Message udpatedMessage = messageService.update(id, param_message);
        log.info("End api: Updating an existing message with ID '{}'",id);
        return ResponseEntity.ok(udpatedMessage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a message", description = "Delete an existing message based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "message deleted successfully"),
            @ApiResponse(responseCode = "404", description = "message not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Start api: Delete an existing message with ID '{}'",id);
        messageService.delete(id);
        log.info("End api: Delete an existing message with ID '{}'",id);
        return ResponseEntity.noContent().build();
    }
}


