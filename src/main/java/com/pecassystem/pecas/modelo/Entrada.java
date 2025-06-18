package com.pecassystem.pecas.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbentrada")
@Getter
@Setter
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Quantidade de entrada é obrigatória")
    @Column(name = "quantidade_entrada", nullable = false)
    private int quantidadeEntrada;

    @Column(name = "motivo", length = 50)
    private String motivo;

    @Column(name = "colaborador", length = 20)
    private String colaborador;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    @Column(name = "observacao", length = 100)
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "idpeca", nullable = false)
    @NotNull(message = "Peça é obrigatória")
    private Peca peca;

    @ManyToOne
    @JoinColumn(name = "idlocacao", nullable = false)
    @NotNull(message = "Locação é obrigatória")
    private Locacao locacao;
}
