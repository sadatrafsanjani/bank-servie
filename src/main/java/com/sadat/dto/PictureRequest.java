package com.sadat.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PictureRequest {

    private byte[] picture;
}
