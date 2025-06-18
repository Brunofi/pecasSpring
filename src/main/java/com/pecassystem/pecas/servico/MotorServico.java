package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecassystem.pecas.repositorio.MotorRepositorio;
import com.pecassystem.pecas.modelo.Motor;

@Service
public class MotorServico {

    @Autowired
    private MotorRepositorio motorRepositorio;

    // Cadastra um novo motor
    public Motor cadastrar(Motor motor) {
        return motorRepositorio.save(motor);
    }

    // Busca um motor por ID
    public Motor buscarPorId(int id) {
        return motorRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Motor não encontrado com o ID: " + id));
    }

    // Lista todos os motores
    public Iterable<Motor> listar() {
        return motorRepositorio.findAll();
    }

    // Remove um motor por ID
    public void remover(int id) {
        motorRepositorio.deleteById(id);
    }
}
