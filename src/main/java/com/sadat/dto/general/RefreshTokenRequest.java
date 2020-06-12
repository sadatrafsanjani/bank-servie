package com.sadat.dto.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenRequest {

    private String username;
    @NotBlank
    private String refreshToken;
}
