package com.pecassystem.pecas.modelo;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "tbpecas")
@Getter
@Setter
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank (message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Partnumber é obrigatório")
    @Column(nullable = false)
    private String partnumber;

    @NotBlank(message = "Peso é obrigatório")
    private String peso;

    @NotBlank(message = "NCM é obrigatório")
    private String ncm;

  
    @NotNull(message = "Estado é obrigatório")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @NotBlank(message = "Modelo do carro é obrigatório")
    private String modelocarro;

    @NotBlank(message = "Subsistema é obrigatório")
    private String subsistema;

    @NotBlank(message = "Fabricante é obrigatório")
    private String fabricante;

    @NotNull(message = "Quantidade mínima é obrigatória")
    private Integer qtdmin;

    @NotNull(message = "Quantidade média é obrigatória")
    private Integer qtdmed;

    @NotNull(message = "Quantidade máxima é obrigatória")
    private Integer qtdmax;

    @NotNull(message = "Preço é obrigatório")
    private BigDecimal preco;

    private String ptnumberSimilar;

    public enum Estado {
        NOVA, REC
    }

    
}
