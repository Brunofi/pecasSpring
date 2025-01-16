package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecassystem.pecas.repositorio.EstoqueRepositorio;
import com.pecassystem.pecas.modelo.Estoque;

import java.util.List;

@Service
public class EstoqueServico {

    @Autowired
    private EstoqueRepositorio estoqueRepositorio;

    // Lista todos os estoques
    public Iterable<Estoque> listar() {
        return estoqueRepositorio.findAll();
    }

    // Busca estoque pelo ID
    public Estoque buscarPorId(int id) {
        try {
            return estoqueRepositorio.findById(id)
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado com o ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estoque: " + e.getMessage());
        }
    }

    // Cadastra um novo estoque
    public Estoque cadastrar(Estoque estoque) {
        try {
            return estoqueRepositorio.save(estoque);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o estoque: " + e.getMessage());
        }
    }

    // Altera um estoque existente
    public Estoque alterar(Estoque estoque) {
        try {
            return estoqueRepositorio.save(estoque);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar o estoque: " + e.getMessage());
        }
    }

    // Remove um estoque por ID
    public void remover(int id) {
        if (estoqueRepositorio.existsById(id)) {
            estoqueRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("Estoque não encontrado com o ID: " + id);
        }
    }
/* 
    // Busca estoques pelo part number da peça
    public List<Estoque> buscarPorPartNumber(String partNumber) {
        try {
            return estoqueRepositorio.findByPartNumber(partNumber);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estoques pelo part number: " + e.getMessage());
        }
    }*/

    public List<Estoque> buscarPorPartNumber(String partNumber) {
        try {
            List<Estoque> estoques = estoqueRepositorio.findByPartNumber(partNumber);
            System.out.println("Resultado: " + estoques);
            return estoques;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estoques pelo part number: " + e.getMessage());
        }
    }
    

    // Busca estoques pelo nome da peça
    public List<Estoque> buscarPorNomePeca(String nomePeca) {
        try {
            return estoqueRepositorio.findByNomePeca(nomePeca);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estoques pelo nome da peça: " + e.getMessage());
        }
    }

    // Busca estoques pela locação
    public List<Estoque> buscarPorLocacao(String locacao) {
        try {
            return estoqueRepositorio.findByLocacao(locacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estoques pela locação: " + e.getMessage());
        }
    }
}
