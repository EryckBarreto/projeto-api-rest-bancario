package com.db1group.apirest.controllers;

import com.db1group.apirest.models.Contrato;
import com.db1group.apirest.repositories.ContratoRepository;
import com.db1group.apirest.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;
    @GetMapping
    public List<Contrato> listarContratos() {
        return contratoRepository.findAll();
    }

    @GetMapping("/{numeroContrato}")
    public ResponseEntity<Object> obterContrato(@PathVariable Integer numeroContrato) {
        Optional<Contrato> contrato = contratoRepository.findById(numeroContrato);

        if(!contrato.isPresent()) {
            return ResponseHandler.gerarResposta("Contrato não encontrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(contrato.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> criarContrato(@RequestBody Contrato contrato) {

        if (contrato.getTotalParcelas() == null) {
            return ResponseHandler.gerarResposta("Número de parcelas é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (contrato.getCpf() == null) {
            return ResponseHandler.gerarResposta("CPF do contrato é obrigatório", HttpStatus.BAD_REQUEST);
        }


        Contrato newContrato = contratoRepository.save(contrato);
        return new ResponseEntity<Object>(newContrato, HttpStatus.CREATED);
    }

    @PutMapping("/{numeroContrato}")
    public ResponseEntity<Object> atualizarContrato(@PathVariable Integer numeroContrato, @RequestBody Contrato contrato) {

        Optional<Contrato> oldContrato = contratoRepository.findById(numeroContrato);

        if(!oldContrato.isPresent()) {
            return ResponseHandler.gerarResposta("Contrato não encontrado.", HttpStatus.NOT_FOUND);
        }

        if (contrato.getTotalParcelas() == null) {
            return ResponseHandler.gerarResposta("Número de parcelas é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (contrato.getCpf() == null) {
            return ResponseHandler.gerarResposta("CPF do contrato é obrigatório", HttpStatus.BAD_REQUEST);
        }

        Contrato updateContrato = oldContrato.get();
        updateContrato.setTotalParcelas(contrato.getTotalParcelas());
        updateContrato.setCpf(contrato.getCpf());

        contratoRepository.save(updateContrato);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{numeroContrato}")
    public ResponseEntity<Object> deletarContrato(@PathVariable Integer numeroContrato) {

        Optional<Contrato> oldContrato = contratoRepository.findById(numeroContrato);

        if(!oldContrato.isPresent()) {
            return ResponseHandler.gerarResposta("Contrato não encontrado.", HttpStatus.NOT_FOUND);
        }

        contratoRepository.delete(oldContrato.get());
        return ResponseEntity.noContent().build();
    }
}
