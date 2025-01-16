package com.pecassystem.pecas.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.pecassystem.pecas.modelo.Locacao;


public interface LocacaoRepositório extends CrudRepository<Locacao, Integer> {

    Iterable<Locacao> findByLocacaoContaining(String locacao);   
    
    Locacao findById(int id);

}
