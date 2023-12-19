package com.db1group.apirest.controllers;

import com.db1group.apirest.models.Convenio;
import com.db1group.apirest.repositories.ConvenioRepository;
import com.db1group.apirest.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/convenios")
public class ConvenioController {

    @Autowired
    private ConvenioRepository convenioRepository;
    @GetMapping
    public List<Convenio> listarConvenios() {
        return convenioRepository.findAll();
    }
    //    consultar um convenio
    @GetMapping("/{id}")
    public ResponseEntity<Object> obterConvenio(@PathVariable Integer id) {
        Optional<Convenio> convenio = convenioRepository.findById(id);

        if(!convenio.isPresent()) {
            return ResponseHandler.gerarResposta("Convênio não encontrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(convenio.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> criarConvenio(@RequestBody Convenio convenio) {

        if (convenio.getNomeConvenio() == null) {
            return ResponseHandler.gerarResposta("Nome do convênio é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (convenio.getCnpj() == null) {
            return ResponseHandler.gerarResposta("CNPJ do convenio é obrigatório", HttpStatus.BAD_REQUEST);
        }

        Convenio newConvenio = convenioRepository.save(convenio);
        return new ResponseEntity<Object>(newConvenio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarConvenio(@PathVariable Integer id, @RequestBody Convenio convenio) {

        Optional<Convenio> oldConvenio = convenioRepository.findById(id);

        if(!oldConvenio.isPresent()) {
            return ResponseHandler.gerarResposta("Convênio não encontrado.", HttpStatus.NOT_FOUND);
        }

        if (convenio.getNomeConvenio() == null) {
            return ResponseHandler.gerarResposta("Nome do convênio é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (convenio.getCnpj() == null) {
            return ResponseHandler.gerarResposta("CNPJ do convenio é obrigatório", HttpStatus.BAD_REQUEST);
        }

        Convenio updateConvenio = oldConvenio.get();
        updateConvenio.setNomeConvenio(convenio.getNomeConvenio());
        updateConvenio.setCnpj(convenio.getCnpj());

        convenioRepository.save(updateConvenio);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarConvenio(@PathVariable Integer id) {

        Optional<Convenio> oldConvenio = convenioRepository.findById(id);

        if(!oldConvenio.isPresent()) {
            return ResponseHandler.gerarResposta("Convênio não encontrado.", HttpStatus.NOT_FOUND);
        }

        convenioRepository.delete(oldConvenio.get());
        return ResponseEntity.noContent().build();
    }
}
