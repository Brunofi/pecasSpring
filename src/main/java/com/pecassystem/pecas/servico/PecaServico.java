package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.pecassystem.pecas.modelo.Peca;

import com.pecassystem.pecas.repositorio.PecaRepositorio;

@Service
public class PecaServico {

    @Autowired
    private PecaRepositorio pecaRepositorio;

    

    //Lista todas as peças
    public Iterable<Peca> listar(){
        return pecaRepositorio.findAll();
    }

    

    public Peca cadastrar(Peca peca) {
        try {
            return pecaRepositorio.save(peca);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a peça: " + e.getMessage());
        }
    }
    

    public Peca alterar(Peca peca) {
        try {
            return pecaRepositorio.save(peca);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar a peça: " + e.getMessage());
        }
    }
    

    public void remover(int id) {
        if (pecaRepositorio.existsById(id)) {
            pecaRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("Peça não encontrada com o ID: " + id);
        }
    }
    

       

    
}
