package com.learn.boardserver.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequest {
    @Valid
    @NotNull
    private String userId;

    @Valid
    @NotNull
    private String password;
}
