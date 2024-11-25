package com.brunov.mongodb.relacionamentos.controller;

import com.brunov.mongodb.relacionamentos.models.Perfil;
import com.brunov.mongodb.relacionamentos.models.Usuario;
import com.brunov.mongodb.relacionamentos.repositories.PerfilRepository;
import com.brunov.mongodb.relacionamentos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public Perfil create(@RequestBody Perfil perfil) {
        if (perfil.getBio() == null || perfil.getBio().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'bio' é obrigatório.");
        }
        if (perfil.getAvatarUri() == null || perfil.getAvatarUri().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'avatarUri' é obrigatório.");
        }
        return perfilRepository.save(perfil);
    }

    @GetMapping("/{id}")
    public Perfil getPerfilById(@PathVariable String id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado."));
    }

    @GetMapping
    public List<Perfil> listarPerfis() {
        return perfilRepository.findAll();
    }

    @PutMapping("/{id}")
    public Perfil atualizarPerfil(@RequestBody Perfil perfilAtualizado, @PathVariable String id) {
        Perfil perfilExistente = getPerfilById(id);
        if (perfilAtualizado.getBio() != null && !perfilAtualizado.getBio().isEmpty()) {
            perfilExistente.setBio(perfilAtualizado.getBio());
        }
        if (perfilAtualizado.getAvatarUri() != null && !perfilAtualizado.getAvatarUri().isEmpty()) {
            perfilExistente.setAvatarUri(perfilAtualizado.getAvatarUri());
        }

        return perfilRepository.save(perfilExistente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerfil(@PathVariable String id) {
        Perfil perfil = getPerfilById(id);

        List<Usuario> usuariosComEstePerfil = usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getPerfil() != null && usuario.getPerfil().getId().equals(id)).collect(Collectors.toList());

        if (!usuariosComEstePerfil.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível excluir o perfil porque está associado a um ou mais usuários.");
        }

        perfilRepository.deleteById(id);
    }
}
