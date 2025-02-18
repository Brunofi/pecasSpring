package com.pecassystem.pecas.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pecassystem.pecas.modelo.Sessao;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.SessaoServico;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sessoes")
public class SessaoControle {

    @Autowired
    private SessaoServico sessaoServico;

    @Autowired
    private RespostaModelo respostaModelo;

    // Lista todas as sessões
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Sessao>> listar() {
        Iterable<Sessao> sessoes = sessaoServico.listar();
        if (sessoes.iterator().hasNext()) {
            return new ResponseEntity<>(sessoes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Busca uma sessão por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Sessao sessao = sessaoServico.buscarPorId(id);
            return new ResponseEntity<>(sessao, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    // Cadastra uma nova sessão
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Sessao sessao, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Sessao novaSessao = sessaoServico.cadastrar(sessao);
            respostaModelo.setMensagem("Sessão cadastrada com sucesso!");
            respostaModelo.setData(novaSessao);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Altera uma sessão existente
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Sessao sessao, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Sessao sessaoAtualizada = sessaoServico.cadastrar(sessao);
            respostaModelo.setMensagem("Sessão alterada com sucesso!");
            respostaModelo.setData(sessaoAtualizada);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove uma sessão por ID
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            sessaoServico.remover(id);
            respostaModelo.setMensagem("Sessão removida com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}
