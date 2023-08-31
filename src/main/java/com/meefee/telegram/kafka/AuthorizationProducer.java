package com.meefee.telegram.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meefee.telegram.dto.AuthorizationDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationProducer {

    @Value(value = "${kafka.auth.done.topic.name}")
    private String authDoneTopic;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendSuccessfulAuthorization(AuthorizationDataDTO authorizationDataDTO) {
        try {
            String successfulAuthorizationDTOString = objectMapper.writeValueAsString(authorizationDataDTO);
            kafkaTemplate.send(authDoneTopic, successfulAuthorizationDTOString).get();
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            log.error("Error sending converted successful authorization DTO.");
        }
    }

}
