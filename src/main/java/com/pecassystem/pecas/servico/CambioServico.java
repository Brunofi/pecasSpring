package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecassystem.pecas.repositorio.CambioRepositorio;
import com.pecassystem.pecas.modelo.Cambio;

@Service
public class CambioServico {

    @Autowired
    private CambioRepositorio cambioRepositorio;

    // Cadastra um novo câmbio
    public Cambio cadastrar(Cambio cambio) {
        return cambioRepositorio.save(cambio);
    }

    // Busca um câmbio por ID
    public Cambio buscarPorId(int id) {
        return cambioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Câmbio não encontrado com o ID: " + id));
    }

    // Lista todos os câmbios
    public Iterable<Cambio> listar() {
        return cambioRepositorio.findAll();
    }

    // Remove um câmbio por ID
    public void remover(int id) {
        cambioRepositorio.deleteById(id);
    }
}

