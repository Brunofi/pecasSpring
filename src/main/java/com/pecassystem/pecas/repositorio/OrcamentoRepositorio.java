package com.pecassystem.pecas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pecassystem.pecas.modelo.Orcamento;

public interface OrcamentoRepositorio extends JpaRepository<Orcamento, Integer> {

    @Query("SELECT o FROM Orcamento o " +
           "WHERE o.chassis = :chassis " +
           "AND o.etapa = :etapa " +
           "AND (:sessao IS NULL OR o.sessao = :sessao) " + // Corrigido: sessao opcional
           "AND (:motivo IS NULL OR o.motivoConsumo = :motivo)") // Motivo também opcional
    Iterable<Orcamento> buscarPorFiltros(
            @Param("chassis") String chassis,
            @Param("etapa") String etapa,
            @Param("sessao") String sessao, // Agora pode ser null
            @Param("motivo") Orcamento.MotivoConsumo motivo // Pode ser null
    );
}
