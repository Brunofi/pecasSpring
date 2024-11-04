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
import org.springframework.web.bind.annotation.RestController;
import com.pecassystem.pecas.modelo.Peca;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.PecaServico;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pecas")
public class PecaControle {

    @Autowired
    private PecaServico pecaServico;

    @Autowired
    private RespostaModelo respostaModelo;

   
    @GetMapping("/listar")
public ResponseEntity<Iterable<Peca>> listar() {
    Iterable<Peca> pecas = pecaServico.listar();
    
    if (pecas.iterator().hasNext()) {
        return new ResponseEntity<>(pecas, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 se a lista estiver vazia
    }
}


    
    @PostMapping("/cadastrar")
public ResponseEntity<?> cadastrar(@Valid @RequestBody Peca peca, BindingResult result) {

    if (result.hasErrors()) {
        String errorMessage = result.getFieldError().getDefaultMessage();
        respostaModelo.setMensagen(errorMessage);
        return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    try {
        Peca novaPeca = pecaServico.cadastrar(peca);
        return new ResponseEntity<>(novaPeca, HttpStatus.CREATED);
    } catch (RuntimeException e) {
        respostaModelo.setMensagen(e.getMessage());
        return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


@PutMapping("/alterar")
public ResponseEntity<?> alterar(@Valid @RequestBody Peca peca, BindingResult result) {

    // Verifica se há erros de validação
    if (result.hasErrors()) {
        String errorMessage = result.getFieldError().getDefaultMessage();
        respostaModelo.setMensagen(errorMessage);
        return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    try {
        Peca novaPeca = pecaServico.alterar(peca);
        return new ResponseEntity<>(novaPeca, HttpStatus.OK);
    } catch (RuntimeException e) {
        respostaModelo.setMensagen(e.getMessage());
        return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


@DeleteMapping("/remover/{id}")
public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
    try {
        pecaServico.remover(id);
        respostaModelo.setMensagen("Peça removida com sucesso");
        return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
    } catch (RuntimeException e) {
        respostaModelo.setMensagen(e.getMessage());
        return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
    }
}


}



    

