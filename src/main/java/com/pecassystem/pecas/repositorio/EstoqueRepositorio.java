package com.pecassystem.pecas.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pecassystem.pecas.modelo.Estoque;

import java.util.List;

public interface EstoqueRepositorio extends CrudRepository<Estoque, Integer> {

    // Busca estoques pelo part number da peça
    @Query("SELECT e FROM Estoque e JOIN e.peca p WHERE p.partnumber LIKE %:partnumber%")
    List<Estoque> findByPartNumber(@Param("partnumber") String partnumber);

    // Busca estoques pelo nome da peça
    @Query("SELECT e FROM Estoque e WHERE e.peca.nome LIKE %:nome%")
    List<Estoque> findByNomePeca(@Param("nome") String nome);

    // Busca estoques pela locação
    @Query("SELECT e FROM Estoque e WHERE e.locacao.locacao LIKE %:locacao%")
    List<Estoque> findByLocacao(@Param("locacao") String locacao);
}
