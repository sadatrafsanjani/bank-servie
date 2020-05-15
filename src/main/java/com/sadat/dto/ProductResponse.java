package com.sadat.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

    private Long id;
    private String type;
    private String code;
    private String name;
}
