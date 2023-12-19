package com.db1group.apirest.controllers;

import com.db1group.apirest.models.Cliente;
import com.db1group.apirest.repositories.ClienteRepository;
import com.db1group.apirest.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
    //    consultar um cliente
    @GetMapping("/{id}")
    public ResponseEntity<Object> obterCliente(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(!cliente.isPresent()) {
            return ResponseHandler.gerarResposta("Cliente não encontrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(cliente.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> criarCliente(@RequestBody Cliente cliente) {

        if (cliente.getNome() == null) {
            return ResponseHandler.gerarResposta("Nome do cliente é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (cliente.getCpf() == null) {
            return ResponseHandler.gerarResposta("CPF do cliente é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (cliente.getEmail() == null) {
            return ResponseHandler.gerarResposta("Email do cliente é obrigatório", HttpStatus.BAD_REQUEST);
        }

        Cliente newCliente = clienteRepository.save(cliente);
        return new ResponseEntity<Object>(newCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {

        Optional<Cliente> oldCliente = clienteRepository.findById(id);

        if(!oldCliente.isPresent()) {
            return ResponseHandler.gerarResposta("Cliente não encontrato.", HttpStatus.NOT_FOUND);
        }

        if (cliente.getNome() == null) {
            return ResponseHandler.gerarResposta("Nome do cliente é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (cliente.getCpf() == null) {
            return ResponseHandler.gerarResposta("CPF do cliente é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (cliente.getEmail() == null) {
            return ResponseHandler.gerarResposta("Email do cliente é obrigatório", HttpStatus.BAD_REQUEST);
        }

        Cliente updateCliente = oldCliente.get();
        updateCliente.setNome(cliente.getNome());
        updateCliente.setCpf(cliente.getCpf());
        updateCliente.setEmail(cliente.getEmail());

        clienteRepository.save(updateCliente);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable Integer id) {

        Optional<Cliente> oldCliente = clienteRepository.findById(id);

        if(!oldCliente.isPresent()) {
            return ResponseHandler.gerarResposta("Cliente não encontrado.", HttpStatus.NOT_FOUND);
        }

        clienteRepository.delete(oldCliente.get());
        return ResponseEntity.noContent().build();
    }
}
