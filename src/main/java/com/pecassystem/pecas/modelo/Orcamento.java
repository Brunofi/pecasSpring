package com.pecassystem.pecas.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tborcamentos")
@Getter
@Setter
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 30, message = "O Part Number deve ter no máximo 30 caracteres")
    @Column(name = "partnumber", length = 30)
    private String partnumber;

    @Size(max = 60, message = "O nome da peça deve ter no máximo 60 caracteres")
    @Column(name = "nome_peca", length = 60)
    private String nomePeca;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    @NotNull(message = "A quantidade é obrigatória")
    private int quantidade;

    @Size(max = 40, message = "O nome do colaborador de entrega deve ter no máximo 40 caracteres")
    @Column(name = "colaborador_entrega", length = 40)
    private String colaboradorEntrega;

    @NotBlank(message = "O nome do colaborador do pedido é obrigatório")
    @Size(max = 40, message = "O nome do colaborador do pedido deve ter no máximo 40 caracteres")
    @Column(name = "colaborador_pedido", length = 40)
    private String colaboradorPedido;

    @NotNull(message = "O motivo do consumo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_consumo", columnDefinition = "ENUM('AVARIA','MANUTENCAO')")
    private MotivoConsumo motivoConsumo;

    @NotBlank(message = "A etapa é obrigatória")
    @Size(max = 6, message = "A etapa deve ter no máximo 6 caracteres")
    @Column(length = 6)
    private String etapa;

    @NotBlank(message = "A sessão é obrigatória")
    @Size(max = 20, message = "A sessão deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String sessao;

    @NotBlank(message = "O campo chassis é obrigatório")
    @Size(max = 60, message = "O chassis deve ter no máximo 60 caracteres")
    @Column(length = 60)
    private String chassis;

    @Size(max = 4, message = "O campo eixo/lado deve ter no máximo 4 caracteres")
    @Column(name = "eixo_lado", length = 4)
    private String eixoLado;

    @Size(max = 50, message = "O número do motor ou câmbio deve ter no máximo 50 caracteres")
    @Column(name = "numero_motor_cambio", length = 50)
    private String numeroMotorCambio;

    @Size(max = 10, message = "O estado da peça deve ter no máximo 10 caracteres")
    @Column(name = "estado_peca", length = 10)
    private String estadoPeca;

    @Size(max = 30, message = "O status da peça deve ter no máximo 30 caracteres")
    @Column(name = "status_peca", length = 30)
    private String statusPeca;

    public enum MotivoConsumo {
        AVARIA,
        MANUTENCAO
    }
}

