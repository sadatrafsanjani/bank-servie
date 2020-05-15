package com.sadat.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "NID_NO")
    private String nidNo;

    @Column(name = "FATHER_NAME")
    private String fatherName;

    @Column(name = "MOTHER_NAME")
    private String motherName;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "ADDRESS")
    private String address;

}
