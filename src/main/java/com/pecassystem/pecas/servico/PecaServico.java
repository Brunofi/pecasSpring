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

     // Método para buscar peças por partNumber
     public Iterable<Peca> listarPorPartnumber(String partnumber) {
        try {
            return pecaRepositorio.findByPartnumberContaining(partnumber);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao procurar peças: " + e.getMessage());
           
        }
        
    }

    // Método para buscar peças por nome
    public Iterable<Peca> listarPorNome(String nome) {
        try {
            return pecaRepositorio.findByNomeContaining(nome);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao procurar peças: " + e.getMessage());
           
        }
        
    }

    public Peca buscarPorId(int id) {
        try {
            return pecaRepositorio.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar peça: " + e.getMessage());
        }
        
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
