package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pecassystem.pecas.repositorio.EstoqueRepositorio;
import com.pecassystem.pecas.modelo.Estoque;
import com.pecassystem.pecas.modelo.Locacao;
import com.pecassystem.pecas.modelo.Peca;

import java.util.List;

@Service
public class EstoqueServico {

    @Autowired
    private EstoqueRepositorio estoqueRepositorio;

    @Autowired
    private PecaServico pecaServico;

    @Autowired
    private LocacaoServico locacaoServico;

    // Cadastra recebendo IDs
    public Estoque cadastrarComIds(int idPeca, int idLocacao) {
        try {
            Peca peca = pecaServico.buscarPorId(idPeca);
            Locacao locacao = locacaoServico.buscarPorId(idLocacao);

            Estoque estoque = new Estoque();
            estoque.setPeca(peca);
            estoque.setLocacao(locacao);
            estoque.setQuantidade(0);

            return estoqueRepositorio.save(estoque);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar estoque com IDs: " + e.getMessage());
        }
    }

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

    // Busca estoques pelo nome da peça
    public List<Estoque> buscarPorIdPeca(int idPeca) {
        try {
            return estoqueRepositorio.findByPecaId(idPeca);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estoques pelo id da peça: " + e.getMessage());
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

    // Altera a quantidade de uma peça no estoque
    public Estoque alterarQuantidade(int id, int novaQuantidade) {
        try {
            // Busca o estoque pelo ID
            Estoque estoque = estoqueRepositorio.findById(id)
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado com o ID: " + id));

            // Valida a nova quantidade
            if (novaQuantidade < 0) {
                throw new RuntimeException("A quantidade do estoque não pode ser negativa.");
            }

            // Atualiza a quantidade
            estoque.setQuantidade(novaQuantidade);

            // Salva a alteração no banco de dados
            return estoqueRepositorio.save(estoque);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar a quantidade do estoque: " + e.getMessage());
        }
    }

    @Transactional
    public void movimentarEntreLocacoes(int idEstoqueOrigem, int idEstoqueDestino, int quantidade) {
        try {
            if (quantidade <= 0) {
                throw new RuntimeException("A quantidade a ser transferida deve ser maior que 0.");
            }

            Estoque estoqueOrigem = buscarPorId(idEstoqueOrigem);
            Estoque estoqueDestino = buscarPorId(idEstoqueDestino);

            if (estoqueOrigem.getQuantidade() < quantidade) {
                throw new RuntimeException("Saldo insuficiente na locação de origem.");
            }

            // Validação de segurança para evitar NullPointerException
            if (estoqueOrigem.getPeca() == null || estoqueDestino.getPeca() == null) {
                throw new RuntimeException("Erro de integridade: Peça não associada ao estoque.");
            }

            if (estoqueOrigem.getPeca().getId() != estoqueDestino.getPeca().getId()) {
                throw new RuntimeException(
                        "Não é possível movimentar entre peças diferentes (verifique o PartNumber).");
            }

            estoqueOrigem.setQuantidade(estoqueOrigem.getQuantidade() - quantidade);
            estoqueDestino.setQuantidade(estoqueDestino.getQuantidade() + quantidade);

            estoqueRepositorio.save(estoqueOrigem);
            estoqueRepositorio.save(estoqueDestino);
        } catch (RuntimeException e) {
            throw e; // Relança exceções de tempo de execução já tratadas (ex: saldo insuficiente)
        } catch (Exception e) {
            e.printStackTrace(); // Log no servidor para debug
            throw new RuntimeException("Erro ao realizar movimentação: " + e.getMessage());
        }
    }
}
