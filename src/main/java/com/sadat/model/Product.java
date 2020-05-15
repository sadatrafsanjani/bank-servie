package com.sadat.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NATURE_ID")
    private Nature nature;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;
}
