package com.db1group.apirest.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Usuario {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column()
    private String nome;

    @Getter
    @Setter
    @Column
    private String cpf;

    @Getter
    @Setter
    @Column()
    private String emailAcesso;

    @Getter
    @Setter
    @Column()
    private String senhaAcesso;
}
