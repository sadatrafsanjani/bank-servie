package com.sadat.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadRequest {

    private byte[] nid;
    private byte[] picture;
}
