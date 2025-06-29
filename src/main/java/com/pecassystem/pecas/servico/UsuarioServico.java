package com.pecassystem.pecas.servico;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import com.pecassystem.pecas.modelo.Usuario;
import com.pecassystem.pecas.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    public Usuario cadastrar(Usuario usuario) throws Exception {
        return usuarioRepositorio.save(usuario);
    }

    public Iterable<Usuario> listar() {
        return usuarioRepositorio.findAll();
    }

    public Usuario alterar(Usuario usuario) throws Exception {
        return usuarioRepositorio.save(usuario);
    }

    public void remover(int id) throws Exception {
        usuarioRepositorio.deleteById(id);
    }

    public Optional<Usuario> buscarPorLoginESenha(String login, String senha) {
        return usuarioRepositorio.findByLoginAndSenha(login, senha);
    }

    public Optional<Usuario> buscarPorEmailESenha(String email, String senha) {
        return usuarioRepositorio.findByEmailAndSenha(email, senha);
    }

}
