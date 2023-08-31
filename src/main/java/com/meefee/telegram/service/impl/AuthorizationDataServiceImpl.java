package com.meefee.telegram.service.impl;

import com.meefee.telegram.dto.AuthorizationDataDTO;
import com.meefee.telegram.service.AuthorizationDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthorizationDataServiceImpl implements AuthorizationDataService {

    private final RedisTemplate<Integer, AuthorizationDataDTO> authorizationDataRedisTemplate;

    @Override
    public void addAuthorizationData(Integer code, AuthorizationDataDTO authorizationDataDTO) {
        authorizationDataRedisTemplate.opsForValue().set(code, authorizationDataDTO);
    }

    @Override
    public AuthorizationDataDTO findAuthorizationDataByCode(Integer code) {
        return authorizationDataRedisTemplate.opsForValue().get(code);
    }

    @Override
    public void setAuthorizationDataByCode(Integer code, Long telegramUserId, String firstName, String lastName) {
        var authorizationData = findAuthorizationDataByCode(code);
        authorizationData.setTelegramUserId(telegramUserId);
        authorizationData.setFirstName(firstName);
        authorizationData.setLastName(lastName);
        authorizationDataRedisTemplate.opsForValue().set(code, authorizationData);
    }

    @Override
    public void deleteAuthorizationDataByCode(Integer code) {
        authorizationDataRedisTemplate.opsForValue().getAndDelete(code);
    }

    @Override
    public boolean isValidAuthorizationData(Integer code) {
        var authorizationData = findAuthorizationDataByCode(code);
        return authorizationData != null && authorizationData.getAuthorizationRequestDate().plusMinutes(5).isAfter(LocalDateTime.now());
    }


}
