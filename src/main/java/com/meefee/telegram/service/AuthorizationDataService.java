package com.meefee.telegram.service;

import com.meefee.telegram.dto.AuthorizationDataDTO;

public interface AuthorizationDataService {

    void addAuthorizationData(Integer code, AuthorizationDataDTO authorizationDataDTO);
    AuthorizationDataDTO findAuthorizationDataByCode(Integer code);
    void setAuthorizationDataByCode(Integer code, Long telegramUserId, String firstName, String lastName);
    void deleteAuthorizationDataByCode(Integer code);
    boolean isValidAuthorizationData(Integer code);

}
