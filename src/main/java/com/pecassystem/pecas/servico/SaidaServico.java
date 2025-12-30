package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pecassystem.pecas.repositorio.SaidaRepositorio;
import com.pecassystem.pecas.modelo.Estoque;
import com.pecassystem.pecas.modelo.Saida;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class SaidaServico {

    @Autowired
    private SaidaRepositorio saidaRepositorio;

    @Autowired
    private EstoqueServico estoqueServico;

    @Transactional
    public Saida cadastrar(Saida saida) {
        try {
            saida.setDataSaida(LocalDateTime.now());
            // Cadastra a saída no banco de dados
            Saida saidaCadastrada = saidaRepositorio.save(saida);

            // Verifica se o tipo de consumo não é "Vale-Peça"
            if (!saida.getTipoConsumo().equals("Vale-Peça")) {
                // Busca o estoque pelo ID
                Estoque estoque = estoqueServico.buscarPorId(saida.getEstoque().getId());

                // Verifica se o estoque existe
                if (estoque != null) {
                    // Calcula a nova quantidade no estoque
                    int quantidadeAtual = estoque.getQuantidade();
                    int quantidadeSaida = saida.getQuantidade();
                    int novaQuantidade = quantidadeAtual - quantidadeSaida;

                    // Valida a nova quantidade
                    if (novaQuantidade < 0) {
                        throw new RuntimeException("Quantidade insuficiente no estoque.");
                    }

                    // Atualiza a quantidade no estoque
                    estoqueServico.alterarQuantidade(estoque.getId(), novaQuantidade);
                } else {
                    throw new RuntimeException("Estoque não encontrado para a peça e locação informadas.");
                }
            }

            return saidaCadastrada;
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

    // Lista saídas por Chassis e Etapa
    public List<Saida> listarPorChassisEEtapa(String chassis, String etapa) {
        try {
            return saidaRepositorio.findByChassisAndEtapa(chassis, etapa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar saídas por chassis e etapa: " + e.getMessage());
        }
    }

    // Desfaz um lançamento de saída
    @Transactional
    public void desfazerLancamento(int idSaida) {
        try {
            // 1. Busca a Saída pelo ID
            Saida saida = buscarPorId(idSaida);

            // 2. Recupere o Estoque associado
            Estoque estoque = saida.getEstoque();

            // Verifica se o estoque ainda existe (embora o relacionamento JPA deva
            // garantir, é bom validar)
            if (estoque == null) {
                throw new RuntimeException("Estoque associado à saída não encontrado.");
            }

            // 3. Calcule a nova quantidade do estoque (SOMA)
            int quantidadeAtual = estoque.getQuantidade();
            int quantidadeDevolvida = saida.getQuantidade();
            int novaQuantidade = quantidadeAtual + quantidadeDevolvida;

            // 4. Atualize o estoque
            estoqueServico.alterarQuantidade(estoque.getId(), novaQuantidade);

            // 5. Delete o registro da saída
            saidaRepositorio.delete(saida);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao desfazer lançamento: " + e.getMessage());
        }
    }
}
