package com.brunov.mongodb.relacionamentos.controller;

import com.brunov.mongodb.relacionamentos.models.Perfil;
import com.brunov.mongodb.relacionamentos.models.Postagem;
import com.brunov.mongodb.relacionamentos.models.Usuario;
import com.brunov.mongodb.relacionamentos.repositories.PerfilRepository;
import com.brunov.mongodb.relacionamentos.repositories.PostagemRepository;
import com.brunov.mongodb.relacionamentos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PostagemRepository postagemRepository;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        if (usuario.getPerfil() != null && usuario.getPerfil().getId() == null) {
            Perfil perfilSalvo = perfilRepository.save(usuario.getPerfil());
            usuario.setPerfil(perfilSalvo);
        }

        if (usuario.getPostagens() != null && !usuario.getPostagens().isEmpty()) {
            List<Postagem> postagens = usuario.getPostagens().stream()
                    .map(postagemRepository::save)
                    .collect(Collectors.toList());
            usuario.setPostagens(postagens);
        }

        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@RequestBody Usuario userAtualizado, @PathVariable String id) {
        Usuario usuario = getById(id);

        if (userAtualizado.getNome() != null && !userAtualizado.getNome().isEmpty()) {
            usuario.setNome(userAtualizado.getNome());
        }

        if (userAtualizado.getPerfil() != null) {
            Perfil perfil = userAtualizado.getPerfil();
            if (perfil.getId() == null) {
                perfil = perfilRepository.save(perfil);
            }
            usuario.setPerfil(perfil);
        }

        if (userAtualizado.getPostagens() != null && !userAtualizado.getPostagens().isEmpty()) {
            List<Postagem> postagens = userAtualizado.getPostagens().stream()
                    .map(postagem -> {
                        if (postagem.getId() == null) {
                            return postagemRepository.save(postagem);
                        }
                        return postagem;
                    })
                    .collect(Collectors.toList());
            usuario.setPostagens(postagens);
        }

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        Usuario usuario = getById(id);

        // Remove associações antes de excluir
        if (usuario.getPerfil() != null) {
            perfilRepository.deleteById(usuario.getPerfil().getId());
        }
        if (usuario.getPostagens() != null && !usuario.getPostagens().isEmpty()) {
            usuario.getPostagens().forEach(postagem -> postagemRepository.deleteById(postagem.getId()));
        }

        usuarioRepository.deleteById(id);
    }
}
