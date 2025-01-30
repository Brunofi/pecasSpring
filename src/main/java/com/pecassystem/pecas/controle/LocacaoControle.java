package com.pecassystem.pecas.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pecassystem.pecas.modelo.Locacao;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.LocacaoServico;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/locacoes")
public class LocacaoControle {

    @Autowired
    private LocacaoServico locacaoServico;

    @Autowired
    private RespostaModelo respostaModelo;

    // Lista locações com filtros opcionais
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Locacao>> listar(
            @RequestParam(required = false) String locacao) {

        Iterable<Locacao> locacoes;

        if (locacao != null && !locacao.isEmpty()) {
            locacoes = locacaoServico.listarPorLocacao(locacao);
        } else {
            locacoes = locacaoServico.listar();
        }

        if (locacoes.iterator().hasNext()) {
            return new ResponseEntity<>(locacoes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Busca locação por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Locacao locacao = locacaoServico.buscarPorId(id);
            return new ResponseEntity<>(locacao, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    // Cadastra uma nova locação
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Locacao locacao, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Locacao novaLocacao = locacaoServico.cadastrar(locacao);
            respostaModelo.setMensagem("Locação cadastrada com sucesso!");
            respostaModelo.setData(novaLocacao);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Altera uma locação existente
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Locacao locacao, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Locacao novaLocacao = locacaoServico.alterar(locacao);
            respostaModelo.setMensagem("Locação alterada com sucesso!");
            respostaModelo.setData(novaLocacao);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove uma locação por ID
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            locacaoServico.remover(id);
            respostaModelo.setMensagem("Locação removida com sucesso");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}
