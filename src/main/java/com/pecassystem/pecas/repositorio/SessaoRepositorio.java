package com.pecassystem.pecas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pecassystem.pecas.modelo.Sessao;

public interface SessaoRepositorio extends JpaRepository<Sessao, Integer> {
}
