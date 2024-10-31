package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.modelo.Usuario;
import com.pecassystem.pecas.repositorio.UsuarioRepositorio;

public class UsuarioServico {

    @Autowired
    private RespostaModelo respostaModelo;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Iterable<Usuario> listar(){
        return usuarioRepositorio.findAll();
    }

    public ResponseEntity<?> cadastrar(Usuario usuario){

        try {
            Usuario novUsuario = usuarioRepositorio.save(usuario);
            return new ResponseEntity<>(novUsuario,HttpStatus.CREATED);
        } catch (Exception e) {
            respostaModelo.setMensagen("Erro ao  cadastrar usuario" + e.getMessage());
            return new ResponseEntity<>(respostaModelo,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    

}
