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
    public Iterable<Peca> listar(){
        return pecaServico.listar();
    }

    
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Peca peca, BindingResult result) {

        // Verifica se há erros de validação
        if (result.hasErrors()) {
            // Captura a primeira mensagem de erro
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagen(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        // Caso não haja erros, chama o serviço para cadastrar a peça
        return pecaServico.cadastrar(peca);
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@Valid @RequestBody Peca peca, BindingResult result) {

        // Verifica se há erros de validação
        if (result.hasErrors()) {
            // Captura a primeira mensagem de erro
            String errorMessage = result.getFieldError().getDefaultMessage();
            respostaModelo.setMensagen(errorMessage);
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        // Caso não haja erros, chama o serviço para cadastrar a peça
        return pecaServico.alterar(peca);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable int id){
        return pecaServico.remover(id);
    }

}



    

