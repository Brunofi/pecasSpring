package com.pecassystem.pecas.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pecassystem.pecas.modelo.Peca;

@Repository
public interface PecaRepositorio extends CrudRepository<Peca,Integer>{
    
}
