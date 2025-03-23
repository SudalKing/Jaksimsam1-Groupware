package com.jaksimsam1.userservice.infra.messaging.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
public class UserCreateEvent {
    private UUID userId;
    private String email;
    private String password;

    @JsonCreator
    public UserCreateEvent(@JsonProperty("userId") UUID userId,
                           @JsonProperty("email") String email,
                           @JsonProperty("password") String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
}
