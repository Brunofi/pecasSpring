package com.pecassystem.pecas.controle;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pecassystem.pecas.modelo.Entrada;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.EntradaServico;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/entradas")
public class EntradaControle {

    @Autowired
    private EntradaServico entradaServico;

    @Autowired
    private RespostaModelo respostaModelo;

    @PostMapping
    public ResponseEntity<RespostaModelo> cadastrar(@RequestBody Entrada entrada, @RequestParam int idEstoque) {
        respostaModelo = new RespostaModelo();
        try {
            // Define a data de entrada como agora
            entrada.setDataEntrada(LocalDateTime.now());
            
            Entrada entradaCadastrada = entradaServico.cadastrar(entrada, idEstoque);

            respostaModelo.setMensagem("Entrada cadastrada com sucesso! Estoque atualizado.");
            respostaModelo.setData(entradaCadastrada);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<RespostaModelo> listar() {
        respostaModelo = new RespostaModelo();
        try {
            List<Entrada> entradas = entradaServico.listar();
            respostaModelo.setData(entradas);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem("Erro ao listar entradas: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaModelo> buscarPorId(@PathVariable int id) {
        respostaModelo = new RespostaModelo();
        try {
            Entrada entrada = entradaServico.buscarPorId(id);
            respostaModelo.setData(entrada);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaModelo> atualizar(@PathVariable int id, @RequestBody Entrada entrada) {
        respostaModelo = new RespostaModelo();
        try {
            entrada.setId(id);
            Entrada entradaAtualizada = entradaServico.atualizar(entrada);
            respostaModelo.setMensagem("Entrada atualizada com sucesso!");
            respostaModelo.setData(entradaAtualizada);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }

      @DeleteMapping("/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        respostaModelo = new RespostaModelo();
        try {
            entradaServico.remover(id);
            respostaModelo.setMensagem("Entrada removida com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    
}
