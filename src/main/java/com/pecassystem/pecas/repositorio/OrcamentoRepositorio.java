package com.pecassystem.pecas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pecassystem.pecas.modelo.Orcamento;

public interface OrcamentoRepositorio extends JpaRepository<Orcamento, Integer> {
}

