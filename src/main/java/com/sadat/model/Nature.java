package com.sadat.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "natures")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Nature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private String type;
}
