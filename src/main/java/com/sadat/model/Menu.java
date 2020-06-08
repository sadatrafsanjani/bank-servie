package com.sadat.model;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "menus")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_INTERFACE")
    private String userInterface;

    @ManyToMany(mappedBy = "menus")
    private Set<User> users = new LinkedHashSet<>();
}