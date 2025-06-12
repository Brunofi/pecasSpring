package com.pecassystem.pecas.controle;

import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private com.pecassystem.pecas.servico.JwtService JwtService;

    @GetMapping("/listar")
    public ResponseEntity<Iterable<Usuario>> listar() {
        Iterable<Usuario> usuarios = usuarioServico.listar();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Usuario novoUsuario = usuarioServico.cadastrar(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            respostaModelo.setMensagem("Erro ao cadastrar usuário: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

        @PutMapping("/alterar")
public ResponseEntity<RespostaModelo> alterar(@Valid @RequestBody Usuario usuario, BindingResult result) {
    respostaModelo = new RespostaModelo(); // Reinicia o modelo de resposta
    
    if (result.hasErrors()) {
        String errorMessage = result.getFieldError().getDefaultMessage();
        respostaModelo.setMensagem(errorMessage);
        return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    try {
        Usuario usuarioAtualizado = usuarioServico.alterar(usuario);
        respostaModelo.setMensagem("Usuário alterado com sucesso!");
        respostaModelo.setData(usuarioAtualizado); // Opcional: incluir os dados atualizados
        return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
    } catch (Exception e) {
        respostaModelo.setMensagem("Erro ao alterar usuário: " + e.getMessage());
        return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            usuarioServico.remover(id);
            respostaModelo.setMensagem("Usuário removido com sucesso.");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (Exception e) {
            respostaModelo.setMensagem("Erro ao remover usuário: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // src/main/java/com/pecassystem/pecas/controle/UsuarioControle.java
    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioServico.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());
        if (usuarioExistente.isPresent()) {
            Usuario usuarioLogado = usuarioExistente.get();
            String token = JwtService.generateToken(usuarioLogado.getLogin(),
             usuarioLogado.getPerfil(),
             usuarioLogado.getNome()
             ); // Passa login e perfil
            
            // Cria um objeto de resposta com token e perfil
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("token", token);
            resposta.put("perfil", usuarioLogado.getPerfil());
            resposta.put("nome", usuarioLogado.getNome());
            
            return ResponseEntity.ok(resposta);
        } else {
            respostaModelo.setMensagem("Login ou senha incorretos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respostaModelo);
        }
    }
}
