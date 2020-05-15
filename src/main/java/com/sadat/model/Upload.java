package com.sadat.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "uploads")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "NID")
    private byte[] nid;

    @Column(name = "PICTURE")
    private byte[] picture;
}
