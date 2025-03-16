package com.pecassystem.pecas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pecassystem.pecas.modelo.Entrada;

public interface EntradaRepositorio extends JpaRepository<Entrada, Integer> {
   
}