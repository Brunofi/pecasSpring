package com.pecassystem.pecas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pecassystem.pecas.modelo.Cambio;
import java.util.Optional;

public interface CambioRepositorio extends JpaRepository<Cambio, Integer> {
    Optional<Cambio> findByNumeroCambio(String numeroCambio);
}
