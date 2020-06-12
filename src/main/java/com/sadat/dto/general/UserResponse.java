package com.sadat.dto.general;

import lombok.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private boolean status;
    private Set<Long> roles;
    private byte[] picture;
    private Set<Long> pages;
}
