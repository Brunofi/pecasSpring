package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecassystem.pecas.repositorio.ChassisRepositorio;
import com.pecassystem.pecas.modelo.Chassis;

@Service
public class ChassisServico {

    @Autowired
    private ChassisRepositorio chassisRepositorio;

    // Cadastra um novo chassis
    public Chassis cadastrar(Chassis chassis) {
        return chassisRepositorio.save(chassis);
    }

    // Busca um chassis por ID
    public Chassis buscarPorId(int id) {
        return chassisRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Chassis não encontrado com o ID: " + id));
    }

    // Lista todos os chassis
    public Iterable<Chassis> listar() {
        return chassisRepositorio.findAll();
    }

    // Remove um chassis por ID
    public void remover(int id) {
        chassisRepositorio.deleteById(id);
    }
}
