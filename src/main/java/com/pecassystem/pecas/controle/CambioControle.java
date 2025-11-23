package com.pecassystem.pecas.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.pecassystem.pecas.modelo.Cambio;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.CambioServico;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cambios")
public class CambioControle {

    @Autowired
    private CambioServico cambioServico;

    @Autowired
    private RespostaModelo respostaModelo;

    // Lista todos os câmbios
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Cambio>> listar() {
        Iterable<Cambio> cambios = cambioServico.listar();
        if (cambios.iterator().hasNext()) {
            return new ResponseEntity<>(cambios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Busca um câmbio por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Cambio cambio = cambioServico.buscarPorId(id);
            return new ResponseEntity<>(cambio, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    // Cadastra um novo câmbio
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Cambio cambio, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Cambio novoCambio = cambioServico.cadastrar(cambio);
            respostaModelo.setMensagem("Câmbio cadastrado com sucesso!");
            respostaModelo.setData(novoCambio);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            respostaModelo.setMensagem("Já existe um câmbio cadastrado com este número.");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Altera um câmbio existente
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Cambio cambio, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Cambio cambioAtualizado = cambioServico.cadastrar(cambio);
            respostaModelo.setMensagem("Câmbio alterado com sucesso!");
            respostaModelo.setData(cambioAtualizado);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            respostaModelo.setMensagem("Já existe um câmbio cadastrado com este número.");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove um câmbio por ID
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            cambioServico.remover(id);
            respostaModelo.setMensagem("Câmbio removido com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}
