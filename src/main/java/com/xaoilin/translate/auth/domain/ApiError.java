package com.xaoilin.translate.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonDeserialize(builder = ApiError.ApiErrorBuilder.class)
public class ApiError {

    private final int code;
    private final String message;
    private final LocalDateTime timestamp;

}
