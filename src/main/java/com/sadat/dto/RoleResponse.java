package com.sadat.dto;

import lombok.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleResponse {

    private Long id;
    private String role;
    private Set<String> pages;
}
