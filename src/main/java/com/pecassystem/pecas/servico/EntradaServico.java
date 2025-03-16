package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pecassystem.pecas.repositorio.EntradaRepositorio;
import com.pecassystem.pecas.modelo.Estoque;
import com.pecassystem.pecas.modelo.Entrada;

import java.util.List;

@Service
public class EntradaServico {

    @Autowired
    private EntradaRepositorio entradaRepositorio;

    @Autowired
    private EstoqueServico estoqueServico;

    @Transactional
    public Entrada cadastrar(Entrada entrada, int idEstoque) {
        try {
            // Cadastra a entrada no banco de dados
            Entrada entradaCadastrada = entradaRepositorio.save(entrada);

            // Busca o estoque pelo ID da peça e locação
            Estoque estoque = estoqueServico.buscarPorId(idEstoque);

            // Verifica se o estoque existe
            if (estoque != null) {
                // Calcula a nova quantidade no estoque
                int quantidadeAtual = estoque.getQuantidade();
                int quantidadeEntrada = entrada.getQuantidadeEntrada();
                int novaQuantidade = quantidadeAtual + quantidadeEntrada;

                // Atualiza a quantidade no estoque
                estoqueServico.alterarQuantidade(estoque.getId(), novaQuantidade);
            } else {
                // Se o estoque não existir, cria um novo registro de estoque
                Estoque novoEstoque = new Estoque();
                novoEstoque.setPeca(entrada.getPeca());
                novoEstoque.setLocacao(entrada.getLocacao());
                novoEstoque.setQuantidade(entrada.getQuantidadeEntrada());
                estoqueServico.cadastrar(novoEstoque);
            }

            return entradaCadastrada;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar entrada: " + e.getMessage());
        }
    }

    // Lista todas as entradas
    public List<Entrada> listar() {
        try {
            return entradaRepositorio.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar entradas: " + e.getMessage());
        }
    }

    // Busca uma entrada por ID
    public Entrada buscarPorId(int id) {
        try {
            return entradaRepositorio.findById(id)
                    .orElseThrow(() -> new RuntimeException("Entrada não encontrada com o ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar entrada: " + e.getMessage());
        }
    }

    // Atualiza uma entrada existente
    public Entrada atualizar(Entrada entrada) {
        try {
            return entradaRepositorio.save(entrada);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar entrada: " + e.getMessage());
        }
    }

    // Remove uma entrada por ID
    public void remover(int id) {
        try {
            entradaRepositorio.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover entrada: " + e.getMessage());
        }
    }
}
