package com.pecassystem.pecas.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.pecassystem.pecas.modelo.Orcamento;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.OrcamentoServico;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orcamentos")
public class OrcamentoControle {

    @Autowired
    private OrcamentoServico orcamentoServico;

    @Autowired
    private RespostaModelo respostaModelo;

    // Lista todos os orçamentos
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Orcamento>> listar() {
        Iterable<Orcamento> orcamentos = orcamentoServico.listar();
        if (orcamentos.iterator().hasNext()) {
            return new ResponseEntity<>(orcamentos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Busca um orçamento por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Orcamento orcamento = orcamentoServico.buscarPorId(id);
            return new ResponseEntity<>(orcamento, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    // Cadastra um novo orçamento
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Orcamento orcamento, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Orcamento novoOrcamento = orcamentoServico.cadastrar(orcamento);
            respostaModelo.setMensagem("Orçamento cadastrado com sucesso!");
            respostaModelo.setData(novoOrcamento);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Altera um orçamento existente
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Orcamento orcamento, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Orcamento orcamentoAtualizado = orcamentoServico.cadastrar(orcamento);
            respostaModelo.setMensagem("Orçamento alterado com sucesso!");
            respostaModelo.setData(orcamentoAtualizado);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove um orçamento por ID
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            orcamentoServico.remover(id);
            respostaModelo.setMensagem("Orçamento removido com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}

