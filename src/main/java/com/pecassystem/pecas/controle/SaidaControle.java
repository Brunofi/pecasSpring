package com.pecassystem.pecas.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pecassystem.pecas.modelo.Saida;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.SaidaServico;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/saidas")
public class SaidaControle {

    @Autowired
    private SaidaServico saidaServico;

    @Autowired
    private RespostaModelo respostaModelo;

    @PostMapping
    public ResponseEntity<RespostaModelo> cadastrar(@RequestBody Saida saida) {
        respostaModelo = new RespostaModelo();
        try {
            Saida saidaCadastrada = saidaServico.cadastrar(saida);

            // Monta a mensagem de sucesso
            String mensagem = "Saída cadastrada com sucesso!";
            if (!saida.getTipoConsumo().equals("Vale-Peça")) {
                mensagem += " Débito no estoque realizado com sucesso.";
            }

            respostaModelo.setMensagem(mensagem);
            respostaModelo.setData(saidaCadastrada);
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
            List<Saida> saidas = saidaServico.listar();
            respostaModelo.setData(saidas);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem("Erro ao listar saídas: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaModelo> buscarPorId(@PathVariable int id) {
        respostaModelo = new RespostaModelo();
        try {
            Saida saida = saidaServico.buscarPorId(id);
            respostaModelo.setData(saida);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaModelo> atualizar(@PathVariable int id, @RequestBody Saida saida) {
        respostaModelo = new RespostaModelo();
        try {
            saida.setId(id);
            Saida saidaAtualizada = saidaServico.atualizar(saida);
            respostaModelo.setMensagem("Saída atualizada com sucesso!");
            respostaModelo.setData(saidaAtualizada);
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
            saidaServico.remover(id);
            respostaModelo.setMensagem("Saída removida com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-lancamentos")
    public ResponseEntity<RespostaModelo> buscarLancamentos(@RequestParam String chassis, @RequestParam String etapa) {
        respostaModelo = new RespostaModelo();
        try {
            List<Saida> saidas = saidaServico.listarPorChassisEEtapa(chassis, etapa);
            respostaModelo.setData(saidas);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem("Erro ao buscar lançamentos: " + e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/desfazer/{id}")
    public ResponseEntity<RespostaModelo> desfazerLancamento(@PathVariable int id) {
        respostaModelo = new RespostaModelo();
        try {
            saidaServico.desfazerLancamento(id);
            respostaModelo.setMensagem("Lançamento desfeito com sucesso! Estoque atualizado.");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }
}
