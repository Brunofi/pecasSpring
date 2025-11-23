package com.pecassystem.pecas.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.pecassystem.pecas.modelo.Motor;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.MotorServico;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/motores")
public class MotorControle {

    @Autowired
    private MotorServico motorServico;

    @Autowired
    private RespostaModelo respostaModelo;

    // Lista todos os motores
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Motor>> listar() {
        Iterable<Motor> motores = motorServico.listar();
        if (motores.iterator().hasNext()) {
            return new ResponseEntity<>(motores, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Busca um motor por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Motor motor = motorServico.buscarPorId(id);
            return new ResponseEntity<>(motor, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    // Cadastra um novo motor
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Motor motor, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Motor novoMotor = motorServico.cadastrar(motor);
            respostaModelo.setMensagem("Motor cadastrado com sucesso!");
            respostaModelo.setData(novoMotor);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            respostaModelo.setMensagem("Já existe um motor cadastrado com este número.");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Altera um motor existente
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Motor motor, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Motor motorAtualizado = motorServico.cadastrar(motor);
            respostaModelo.setMensagem("Motor alterado com sucesso!");
            respostaModelo.setData(motorAtualizado);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            respostaModelo.setMensagem("Já existe um motor cadastrado com este número.");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove um motor por ID
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            motorServico.remover(id);
            respostaModelo.setMensagem("Motor removido com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}
