package com.pecassystem.pecas.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pecassystem.pecas.modelo.Peca;

@Repository
public interface PecaRepositorio extends CrudRepository<Peca, Integer> {

    Iterable<Peca> findByPartnumberContaining(String partnumber);

    Iterable<Peca> findByNomeContaining(String nome);

    Peca findById(int id);

    // NOVO MÉTODO: Busca peças distintas por partnumber
    @Query("SELECT p FROM Peca p WHERE p.id IN (" +
            "SELECT MIN(p2.id) FROM Peca p2 WHERE p2.partnumber LIKE %:partnumber% GROUP BY p2.partnumber" +
            ")")
    Iterable<Peca> findDistinctByPartnumberContaining(@Param("partnumber") String partnumber);
}
