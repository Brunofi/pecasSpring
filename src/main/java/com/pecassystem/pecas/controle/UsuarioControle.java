package com.pecassystem.pecas.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.modelo.Usuario;
import com.pecassystem.pecas.servico.UsuarioServico;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioControle {

    @Autowired
    private UsuarioServico usuarioServico;

    @Autowired
    private RespostaModelo respostaModelo;

    @GetMapping("/listar")
    public ResponseEntity<Iterable<Usuario>> listar() {
        Iterable<Usuario> usuarios = usuarioServico.listar();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagen(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Usuario novoUsuario = usuarioServico.cadastrar(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            respostaModelo.setMensagen("Erro ao cadastrar usuário: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagen(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Usuario usuarioAtualizado = usuarioServico.alterar(usuario);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (Exception e) {
            respostaModelo.setMensagen("Erro ao alterar usuário: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            usuarioServico.remover(id);
            respostaModelo.setMensagen("Usuário removido com sucesso.");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (Exception e) {
            respostaModelo.setMensagen("Erro ao remover usuário: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioServico.buscarPorLoginESenha(usuario.getLogin(),
                usuario.getSenha());
        if (usuarioExistente.isPresent()) {
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            respostaModelo.setMensagen("Login ou senha incorretos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respostaModelo);
        }
    }
}
