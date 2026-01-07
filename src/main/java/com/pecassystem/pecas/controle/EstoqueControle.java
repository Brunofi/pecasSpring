package com.pecassystem.pecas.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pecassystem.pecas.modelo.Estoque;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.servico.EstoqueServico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/estoques")
public class EstoqueControle {

    @Autowired
    private EstoqueServico estoqueServico;
    @Autowired
    private RespostaModelo respostaModelo;

    @GetMapping("/partnumber/{partnumber}")
    public ResponseEntity<List<Estoque>> buscarPorPartNumber(@PathVariable String partnumber) {
        try {
            return ResponseEntity.ok(estoqueServico.buscarPorPartNumber(partnumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
    }

    // Lista todos os estoques
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Estoque>> listar() {
        try {
            return ResponseEntity.ok(estoqueServico.listar());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable int id) {
        try {
            Estoque estoque = estoqueServico.buscarPorId(id);
            return ResponseEntity.ok(estoque);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // Cadastra um novo estoque
    @PostMapping
    public ResponseEntity<Estoque> cadastrar(@RequestBody Estoque estoque) {
        try {
            Estoque estoqueCadastrado = estoqueServico.cadastrar(estoque);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(estoqueCadastrado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    // Altera um estoque existente
    @PutMapping("/{id}")
    public ResponseEntity<Estoque> alterar(@PathVariable int id, @RequestBody Estoque estoque) {
        try {
            estoque.setId(id); // Garante que o ID corresponde ao caminho
            Estoque estoqueAlterado = estoqueServico.alterar(estoque);
            return ResponseEntity.ok(estoqueAlterado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    // Remove um estoque por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable int id) {
        try {
            estoqueServico.remover(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Busca estoques pelo nome da peça
    @GetMapping("/nome/{nomePeca}")
    public ResponseEntity<List<Estoque>> buscarPorNomePeca(@PathVariable String nomePeca) {
        try {
            return ResponseEntity.ok(estoqueServico.buscarPorNomePeca(nomePeca));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Busca estoques pela locação
    @GetMapping("/locacao/{locacao}")
    public ResponseEntity<List<Estoque>> buscarPorLocacao(@PathVariable String locacao) {
        try {
            return ResponseEntity.ok(estoqueServico.buscarPorLocacao(locacao));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/cadastrar-com-ids")
    public ResponseEntity<?> cadastrarComIds(@RequestParam int idPeca, @RequestParam int idLocacao) {
        respostaModelo = new RespostaModelo(); // Criação do modelo de resposta

        try {
            Estoque estoque = estoqueServico.cadastrarComIds(idPeca, idLocacao);
            respostaModelo.setMensagem("Estoque cadastrado com sucesso!");
            respostaModelo.setData(estoque);
            return new ResponseEntity<>(respostaModelo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }

    // Altera a quantidade de uma peça no estoque
    @PutMapping("/alterar-quantidade/{id}")
    public ResponseEntity<?> alterarQuantidade(@PathVariable int id, @RequestParam int quantidade) {
        respostaModelo = new RespostaModelo(); // Criação do modelo de resposta

        try {
            // Chama o serviço para alterar a quantidade
            Estoque estoqueAtualizado = estoqueServico.alterarQuantidade(id, quantidade);

            // Configura a resposta de sucesso
            respostaModelo.setMensagem("Quantidade alterada com sucesso!");
            respostaModelo.setData(estoqueAtualizado);
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Configura a resposta de erro
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/movimentar")
    public ResponseEntity<?> movimentarEntreLocacoes(
            @RequestParam int idOrigem,
            @RequestParam int idDestino,
            @RequestParam int quantidade) {

        respostaModelo = new RespostaModelo();

        try {
            estoqueServico.movimentarEntreLocacoes(idOrigem, idDestino, quantidade);
            respostaModelo.setMensagem("Movimentação realizada com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/trocar-locacao")
    public ResponseEntity<?> trocarLocacao(
            @RequestParam int idEstoque,
            @RequestParam int idNovaLocacao) {

        respostaModelo = new RespostaModelo();

        try {
            estoqueServico.trocarLocacao(idEstoque, idNovaLocacao);
            respostaModelo.setMensagem("Troca de locação realizada com sucesso!");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } catch (RuntimeException e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }

}
