package com.pecassystem.pecas.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbusuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(unique = true, length = 35)
    private String login;

    @Column(length = 25)
    private String senha;

    @Column(nullable = false, length = 15)
    private String perfil;

    @Column(length = 50) 
    private String setor;

}