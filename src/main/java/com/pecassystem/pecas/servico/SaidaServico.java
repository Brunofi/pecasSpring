package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecassystem.pecas.repositorio.SaidaRepositorio;
import com.pecassystem.pecas.modelo.Saida;

import java.util.List;

@Service
public class SaidaServico {

    @Autowired
    private SaidaRepositorio saidaRepositorio;

    // Cadastra uma nova saída
    public Saida cadastrar(Saida saida) {
        try {
            return saidaRepositorio.save(saida);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar saída: " + e.getMessage());
        }
    }

    // Lista todas as saídas
    public List<Saida> listar() {
        try {
            return saidaRepositorio.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar saídas: " + e.getMessage());
        }
    }

    // Busca uma saída por ID
    public Saida buscarPorId(int id) {
        try {
            return saidaRepositorio.findById(id)
                    .orElseThrow(() -> new RuntimeException("Saída não encontrada com o ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar saída: " + e.getMessage());
        }
    }

    // Atualiza uma saída existente
    public Saida atualizar(Saida saida) {
        try {
            return saidaRepositorio.save(saida);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar saída: " + e.getMessage());
        }
    }

    // Remove uma saída por ID
    public void remover(int id) {
        try {
            saidaRepositorio.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover saída: " + e.getMessage());
        }
    }
}
