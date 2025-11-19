package com.pecassystem.pecas.servico;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pecassystem.pecas.modelo.Orcamento;
import com.pecassystem.pecas.repositorio.OrcamentoRepositorio;

@Service
public class OrcamentoServico {

    @Autowired
    private OrcamentoRepositorio orcamentoRepositorio;

    // Cadastra um novo orçamento ou atualiza existente
    public Orcamento cadastrar(Orcamento orcamento) {
        orcamento.setDataPedido(LocalDateTime.now());
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

    public Iterable<Orcamento> listarPorFiltros(
            String chassis,
            String etapa,
            String sessao,
            String motivo) {
        // Validação dos campos obrigatórios
        if (chassis == null || chassis.isBlank() || etapa == null || etapa.isBlank()) {
            throw new RuntimeException("Chassis e etapa são obrigatórios");
        }

        Orcamento.MotivoConsumo motivoEnum = null;

        if (motivo != null && !motivo.isBlank()) {
            try {
                motivoEnum = Orcamento.MotivoConsumo.valueOf(motivo.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Motivo inválido: " + motivo);
            }
        }

        return orcamentoRepositorio.buscarPorFiltros(
                chassis,
                etapa,
                (sessao != null && !sessao.isBlank()) ? sessao : null,
                motivoEnum);
    }

    @Transactional
    public void cancelarSolicitacao(int id) {
        Orcamento orcamento = orcamentoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        // Verifica se pode cancelar
        if (!orcamento.getStatusPeca().equals("PENDENTE") &&
                !orcamento.getStatusPeca().equals("SEPARADA")) {
            throw new RuntimeException("Só é possível cancelar solicitações com status PENDENTE ou SEPARADA");
        }

        orcamento.setStatusPeca("CANCELADA");
        orcamentoRepositorio.save(orcamento);
    }

    public Iterable<Orcamento> listarPorFiltrosFlexiveis(
            String chassis,
            String etapa,
            String sessao,
            String status) {
        
        // Se nenhum filtro foi preenchido, retorna lista vazia
        if ((chassis == null || chassis.isBlank()) && 
            (etapa == null || etapa.isBlank()) && 
            (sessao == null || sessao.isBlank()) &&
            (status == null || status.isBlank())) {
            return java.util.Collections.emptyList();
        }

        return orcamentoRepositorio.buscarPorFiltrosFlexiveis(
                (chassis != null && !chassis.isBlank()) ? chassis : null,
                (etapa != null && !etapa.isBlank()) ? etapa : null,
                (sessao != null && !sessao.isBlank()) ? sessao : null,
                (status != null && !status.isBlank()) ? status : null);
    }

}
