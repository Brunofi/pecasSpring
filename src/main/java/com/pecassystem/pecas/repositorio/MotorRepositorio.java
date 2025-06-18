package com.pecassystem.pecas.repositorio;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pecassystem.pecas.modelo.Motor;

public interface MotorRepositorio extends JpaRepository<Motor, Integer> {

    Optional<Motor> findByNumeroMotor(String numeroMotor);
}

