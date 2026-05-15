package com.pecassystem.pecas.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbcontrole_inventario")
@Getter
@Setter
public class ControleInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idestoque", nullable = false)
    private Estoque estoque;

    @Column(name = "quantidade_velha", nullable = false)
    private int quantidadeVelha;

    @Column(name = "quantidade_atual", nullable = false)
    private int quantidadeAtual;

    @Column(name = "nome_colaborador", length = 100)
    private String nomeColaborador;

    @Column(name = "data_inventario", nullable = false)
    private LocalDateTime dataInventario;
}
