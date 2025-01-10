package com.pecassystem.pecas.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pecassystem.pecas.modelo.Peca;
import java.util.List;


@Repository
public interface PecaRepositorio extends CrudRepository<Peca,Integer>{

    Iterable<Peca> findByPartnumberContaining(String partnumber);

    Iterable<Peca> findByNomeContaining(String nome);   
    
    Peca findById(int id);
}
