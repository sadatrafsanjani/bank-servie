package com.sadat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadRequest {

    private Long customerId;
    private byte[] nid;
    private byte[] picture;
}
