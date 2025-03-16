package com.pecassystem.pecas.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pecassystem.pecas.modelo.Usuario;

public interface UsuarioRepositorio extends CrudRepository<Usuario,Integer>{

    Optional<Usuario> findByLoginAndSenha (String login, String senha);

    Optional<Usuario> findByEmailAndSenha (String email, String senha);
    
}
