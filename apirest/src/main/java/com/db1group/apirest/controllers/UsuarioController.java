package com.db1group.apirest.controllers;

import com.db1group.apirest.models.Usuario;
import com.db1group.apirest.repositories.UsuarioRepository;
import com.db1group.apirest.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping
    public List<Usuario> listarUsuario() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterUsuario(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(!usuario.isPresent()) {
            return ResponseHandler.gerarResposta("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(usuario.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario) {

        if (usuario.getNome() == null) {
            return ResponseHandler.gerarResposta("Nome do usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getCpf() == null) {
            return ResponseHandler.gerarResposta("CPF do Usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getEmailAcesso() == null) {
            return ResponseHandler.gerarResposta("Email do Usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getSenhaAcesso() == null) {
            return ResponseHandler.gerarResposta("Senha do Usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        Usuario newUsuario = usuarioRepository.save(usuario);
        return new ResponseEntity<Object>(newUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {

        Optional<Usuario> oldUsuario = usuarioRepository.findById(id);

        if(!oldUsuario.isPresent()) {
            return ResponseHandler.gerarResposta("Usuário não encontrato.", HttpStatus.NOT_FOUND);
        }

        if (usuario.getNome() == null) {
            return ResponseHandler.gerarResposta("Nome do usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getCpf() == null) {
            return ResponseHandler.gerarResposta("CPF do Usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getEmailAcesso() == null) {
            return ResponseHandler.gerarResposta("Email do Usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getSenhaAcesso() == null) {
            return ResponseHandler.gerarResposta("Email do Usuário é obrigatório", HttpStatus.BAD_REQUEST);
        }

        Usuario updateUsuario = oldUsuario.get();
        updateUsuario.setNome(usuario.getNome());
        updateUsuario.setCpf(usuario.getCpf());
        updateUsuario.setEmailAcesso(usuario.getEmailAcesso());
        updateUsuario.setSenhaAcesso(usuario.getSenhaAcesso());

        usuarioRepository.save(updateUsuario);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable Integer id) {

        Optional<Usuario> oldUsuario = usuarioRepository.findById(id);

        if(!oldUsuario.isPresent()) {
            return ResponseHandler.gerarResposta("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }

        usuarioRepository.delete(oldUsuario.get());
        return ResponseEntity.noContent().build();
    }
}

