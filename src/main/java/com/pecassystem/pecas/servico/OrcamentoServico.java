package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecassystem.pecas.modelo.Orcamento;
import com.pecassystem.pecas.repositorio.OrcamentoRepositorio;

@Service
public class OrcamentoServico {

    @Autowired
    private OrcamentoRepositorio orcamentoRepositorio;

    // Cadastra um novo orçamento ou atualiza existente
    public Orcamento cadastrar(Orcamento orcamento) {
        return orcamentoRepositorio.save(orcamento);
    }

    // Busca um orçamento por ID
    public Orcamento buscarPorId(int id) {
        return orcamentoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado com o ID: " + id));
    }

    // Lista todos os orçamentos
    public Iterable<Orcamento> listar() {
        return orcamentoRepositorio.findAll();
    }

    // Remove um orçamento por ID
    public void remover(int id) {
        orcamentoRepositorio.deleteById(id);
    }
}

