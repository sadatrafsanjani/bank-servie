package com.sadat.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {

    private Long id;
    private Long accountTypeId;
    private String accountNo;
    private String accountType;
    private String fullName;
    private String dob;
    private String nidNo;
    private String fatherName;
    private String motherName;
    private String phoneNo;
    private String address;
    private double balance;
    private boolean uploadStatus;
}
