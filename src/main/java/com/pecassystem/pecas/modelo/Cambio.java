package com.pecassystem.pecas.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cambios")
@Getter
@Setter
public class Cambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "O número do câmbio é obrigatório")
    @Size(max = 50, message = "O número do câmbio deve ter no máximo 50 caracteres")
    @Column(name = "numero_cambio", length = 50, unique = true)
    private String numeroCambio;
}
