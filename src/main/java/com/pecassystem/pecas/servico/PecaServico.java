package com.pecassystem.pecas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pecassystem.pecas.modelo.Peca;
import com.pecassystem.pecas.modelo.RespostaModelo;
import com.pecassystem.pecas.repositorio.PecaRepositorio;

@Service
public class PecaServico {

    @Autowired
    private PecaRepositorio pecaRepositorio;

    @Autowired
    private RespostaModelo respostaModelo;

    //Lista todas as peças
    public Iterable<Peca> listar(){
        return pecaRepositorio.findAll();
    }

    

        public ResponseEntity<?> cadastrar(Peca peca) {
            try {
                // Salva a peça no banco de dados
                Peca novaPeca = pecaRepositorio.save(peca);
                return new ResponseEntity<>(novaPeca, HttpStatus.CREATED);
            } catch (Exception e) {
                // Tratamento de erros de persistência ou outros erros
                respostaModelo.setMensagen("Erro ao salvar a peça: " + e.getMessage());
                return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        public ResponseEntity<?> alterar(Peca peca) {
            try {
                // Salva a peça no banco de dados
                Peca novaPeca = pecaRepositorio.save(peca);
                return new ResponseEntity<>(novaPeca, HttpStatus.OK);
            } catch (Exception e) {
                // Tratamento de erros de persistência ou outros erros
                respostaModelo.setMensagen("Erro ao salvar a peça: " + e.getMessage());
                return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        public ResponseEntity<RespostaModelo> remover(int id){

            pecaRepositorio.deleteById(id);

            respostaModelo.setMensagen("Peça removida com Sucesso");
            return new ResponseEntity<RespostaModelo>(respostaModelo,HttpStatus.OK);
        }

       

    
}
