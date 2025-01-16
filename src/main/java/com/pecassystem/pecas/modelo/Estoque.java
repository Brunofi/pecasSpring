package com.pecassystem.pecas.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbestoque")
@Getter
@Setter
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Quantidade é obrigatória")
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "idpeca", nullable = false)
    @NotNull(message = "Peça é obrigatória")
    private Peca peca;

    @ManyToOne
    @JoinColumn(name = "idlocacao", nullable = false)
    @NotNull(message = "Locação é obrigatória")
    private Locacao locacao;

}

