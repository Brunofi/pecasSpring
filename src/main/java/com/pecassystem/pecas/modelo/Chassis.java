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
@Table(name = "chassis")
@Getter
@Setter
public class Chassis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 14, message = "O numeral do chassis deve ter no máximo 14 caracteres")
    @Column(name = "numeral", length = 14)
    private String numeral;
}