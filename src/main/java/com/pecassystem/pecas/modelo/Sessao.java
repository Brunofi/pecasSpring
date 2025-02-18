package com.pecassystem.pecas.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sessoes")
@Getter
@Setter
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 20, message = "A sessão deve ter no máximo 20 caracteres")
    @Column(name = "sessao", length = 20)
    private String sessao;
}
