package com.pecassystem.pecas.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.pecassystem.pecas.modelo.Usuario;

public interface UsuarioRepositorio extends CrudRepository<Usuario,Integer>{
    
}
