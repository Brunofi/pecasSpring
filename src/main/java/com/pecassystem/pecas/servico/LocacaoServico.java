package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecassystem.pecas.repositorio.LocacaoRepositório;
import com.pecassystem.pecas.modelo.Locacao;

@Service
public class LocacaoServico {

    @Autowired
    private LocacaoRepositório locacaoRepositorio;

    // Lista todas as locações
    public Iterable<Locacao> listar() {
        return locacaoRepositorio.findAll();
    }

    // Método para buscar locações por nome
    public Iterable<Locacao> listarPorLocacao(String locacao) {
        try {
            return locacaoRepositorio.findByLocacaoContaining(locacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao procurar locações: " + e.getMessage());
        }
    }


    // Busca locação por ID
    public Locacao buscarPorId(int id) {
        try {
            return locacaoRepositorio.findById(id);
                    
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar locação: " + e.getMessage());
        }
    }

    // Cadastra uma nova locação
    public Locacao cadastrar(Locacao locacao) {
        try {
            return locacaoRepositorio.save(locacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a locação: " + e.getMessage());
        }
    }

    // Altera uma locação existente
    public Locacao alterar(Locacao locacao) {
        try {
            return locacaoRepositorio.save(locacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar a locação: " + e.getMessage());
        }
    }

    // Remove uma locação por ID
    public void remover(int id) {
        if (locacaoRepositorio.existsById(id)) {
            locacaoRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("Locação não encontrada com o ID: " + id);
        }
    }
}