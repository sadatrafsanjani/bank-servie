package com.sadat.dto.banking;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerRequest {

    private Long productId;
    private String fullName;
    private String dob;
    private String nidNo;
    private String fatherName;
    private String motherName;
    private String phoneNo;
    private String address;
}
