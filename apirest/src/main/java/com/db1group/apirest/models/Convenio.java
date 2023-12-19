package com.db1group.apirest.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Convenio {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Getter
    @Setter
    @Column(nullable = false)
    private String nomeConvenio;
    @Getter
    @Setter
    @Column(nullable = false)
    private String cnpj;
}
