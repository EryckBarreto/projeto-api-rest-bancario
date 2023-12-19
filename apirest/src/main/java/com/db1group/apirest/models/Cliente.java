package com.db1group.apirest.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Cliente {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Getter
    @Setter
    @Column(nullable = false)
    private String nome;
    @Getter
    @Setter
    @Column(nullable = false)
    private String cpf;
    @Getter
    @Setter
    @Column()
    private String email;
}
