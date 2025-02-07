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

import com.pecassystem.pecas.modelo.Chassis;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.ChassisServico;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chassis")
public class ChassisControle {

    @Autowired
    private ChassisServico chassisServico;

    @Autowired
    private RespostaModelo respostaModelo;

    // Lista todos os chassis
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Chassis>> listar() {
        Iterable<Chassis> chassis = chassisServico.listar();
        if (chassis.iterator().hasNext()) {
            return new ResponseEntity<>(chassis, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Busca um chassis por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Chassis chassis = chassisServico.buscarPorId(id);
            return new ResponseEntity<>(chassis, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    // Cadastra um novo chassis
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Chassis chassis, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Chassis novoChassis = chassisServico.cadastrar(chassis);
            respostaModelo.setMensagem("Chassis cadastrado com sucesso!");
            respostaModelo.setData(novoChassis);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Altera um chassis existente
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Chassis chassis, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagem(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        try {
            Chassis chassisAtualizado = chassisServico.cadastrar(chassis);
            respostaModelo.setMensagem("Chassis alterado com sucesso!");
            respostaModelo.setData(chassisAtualizado);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove um chassis por ID
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id) {
        try {
            chassisServico.remover(id);
            respostaModelo.setMensagem("Chassis removido com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}