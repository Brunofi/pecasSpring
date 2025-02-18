package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecassystem.pecas.repositorio.SessaoRepositorio;
import com.pecassystem.pecas.modelo.Sessao;

@Service
public class SessaoServico {

    @Autowired
    private SessaoRepositorio sessaoRepositorio;

    // Cadastra uma nova sessão
    public Sessao cadastrar(Sessao sessao) {
        return sessaoRepositorio.save(sessao);
    }

    // Busca uma sessão por ID
    public Sessao buscarPorId(int id) {
        return sessaoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada com o ID: " + id));
    }

    // Lista todas as sessões
    public Iterable<Sessao> listar() {
        return sessaoRepositorio.findAll();
    }

    // Remove uma sessão por ID
    public void remover(int id) {
        sessaoRepositorio.deleteById(id);
    }
}
