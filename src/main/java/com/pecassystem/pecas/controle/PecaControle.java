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

    /*
     * @GetMapping("/listar")
     * public ResponseEntity<Iterable<Peca>> listar() {
     * Iterable<Peca> pecas = pecaServico.listar();
     * 
     * if (pecas.iterator().hasNext()) {
     * return new ResponseEntity<>(pecas, HttpStatus.OK);
     * } else {
     * return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 se a lista
     * estiver vazia
     * }
     * }
     */
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Peca>> listar(
            @RequestParam(required = false) String partnumber,
            @RequestParam(required = false) String nome) {

        Iterable<Peca> pecas;

        if (partnumber != null && !partnumber.isEmpty()) {
            pecas = pecaServico.listarPorPartnumber(partnumber);
        } else if (nome != null && !nome.isEmpty()) {
            pecas = pecaServico.listarPorNome(nome);
        } else {
            pecas = pecaServico.listar();
        }

        if (pecas.iterator().hasNext()) {
            return new ResponseEntity<>(pecas, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Peca peca = pecaServico.buscarPorId(id);
            return new ResponseEntity<>(peca, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cadastrar")
public ResponseEntity<?> cadastrar(@Valid @RequestBody Peca peca, BindingResult result) {
    if (result.hasErrors()) {
        String errorMessage = result.getFieldError().getDefaultMessage();
        respostaModelo.setMensagem(errorMessage);
        return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    try {
        Peca novaPeca = pecaServico.cadastrar(peca);
        respostaModelo.setMensagem("Peça cadastrada com sucesso!");
        respostaModelo.setData(novaPeca); 
        return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
    } catch (RuntimeException e) {
        respostaModelo.setMensagem(e.getMessage());
        return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PutMapping("/alterar")
public ResponseEntity<?> alterar(@Valid @RequestBody Peca peca, BindingResult result) {
    if (result.hasErrors()) {
        String errorMessage = result.getFieldError().getDefaultMessage();
        respostaModelo.setMensagem(errorMessage);
        return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    try {
        Peca novaPeca = pecaServico.alterar(peca);
        respostaModelo.setMensagem("Peça alterada com sucesso!");
        respostaModelo.setData(novaPeca);
        return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
    } catch (RuntimeException e) {
        respostaModelo.setMensagem(e.getMessage());
        return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            pecaServico.remover(id);
            respostaModelo.setMensagem("Peça removida com sucesso");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

}
