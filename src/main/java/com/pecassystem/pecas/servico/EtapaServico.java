package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecassystem.pecas.repositorio.EtapaRepositorio;
import com.pecassystem.pecas.modelo.Etapa;

@Service
public class EtapaServico {

    @Autowired
    private EtapaRepositorio etapaRepositorio;

    // Cadastra uma nova etapa
    public Etapa cadastrar(Etapa etapa) {
        return etapaRepositorio.save(etapa);
    }

    // Busca uma etapa por ID
    public Etapa buscarPorId(int id) {
        return etapaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Etapa não encontrada com o ID: " + id));
    }

    // Lista todas as etapas
    public Iterable<Etapa> listar() {
        return etapaRepositorio.findAll();
    }

    // Remove uma etapa por ID
    public void remover(int id) {
        etapaRepositorio.deleteById(id);
    }
}
