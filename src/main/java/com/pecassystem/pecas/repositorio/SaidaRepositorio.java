package com.pecassystem.pecas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pecassystem.pecas.modelo.Saida;
import java.util.List;

public interface SaidaRepositorio extends JpaRepository<Saida, Integer> {
    List<Saida> findByChassisAndEtapa(String chassis, String etapa);
}
