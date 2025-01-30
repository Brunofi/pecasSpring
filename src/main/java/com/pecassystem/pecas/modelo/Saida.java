package com.pecassystem.pecas.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbsaida")
@Getter
@Setter
public class Saida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Quantidade é obrigatória")
    private int quantidade;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Column(name = "tipo_consumo", length = 30)
    private String tipoConsumo;

    @Column(name = "colaborador_entrega", length = 40, nullable = false)
    private String colaboradorEntrega;

    @Column(name = "colaborador_retirada", length = 40, nullable = false)
    private String colaboradorRetirada;

    @Column(name = "colaborador_lancamento", length = 40)
    private String colaboradorLancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_consumo", nullable = false)
    private MotivoConsumo motivoConsumo;

    @Column(name = "etapa", length = 6, nullable = false)
    private String etapa;

    @Column(name = "sessao", length = 20, nullable = false)
    private String sessao;

    @Column(name = "chassis", length = 12, nullable = false)
    private String chassis;

    @Column(name = "chassis_cedente", length = 12)
    private String chassisCedente;

    @Column(name = "eixo_lado", length = 4)
    private String eixoLado;

    @ManyToOne
    @JoinColumn(name = "idpeca", nullable = false)
    @NotNull(message = "Peça é obrigatória")
    private Peca peca;

    @ManyToOne
    @JoinColumn(name = "idlocacao", nullable = false)
    @NotNull(message = "Locação é obrigatória")
    private Locacao locacao;

    @ManyToOne
    @JoinColumn(name = "idestoque")
    private Estoque estoque;
}

// Enum para o motivo de consumo
enum MotivoConsumo {
    AVARIA,
    MANUTENCAO
}
