package com.sadat.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadResponse {

    private byte[] nid;
    private byte[] picture;
}
