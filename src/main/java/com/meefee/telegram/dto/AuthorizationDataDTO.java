package com.meefee.telegram.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorizationDataDTO {

    private Integer authorizationCode;
    private LocalDateTime authorizationRequestDate;

    private Long telegramUserId;
    private String firstName;
    private String lastName;



}
