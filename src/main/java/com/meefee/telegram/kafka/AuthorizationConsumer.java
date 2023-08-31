package com.meefee.telegram.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meefee.telegram.dto.AuthorizationDataDTO;
import com.meefee.telegram.service.AuthorizationDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationConsumer {

    private final AuthorizationDataService authorizationDataService;

    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${kafka.auth.topic.name}",
            groupId = "${kafka.auth.topic.group-id}"
    )
    public void listerAuthorizationRequest(String authorizationData) {
        try {
            AuthorizationDataDTO authorizationDataDTO = objectMapper.readValue(authorizationData, AuthorizationDataDTO.class);
            authorizationDataService.addAuthorizationData(authorizationDataDTO.getAuthorizationCode(), authorizationDataDTO);
        } catch (JsonProcessingException e) {
            log.error("Error when mapping AuthorizationDataDTO");
        }
    }

}
