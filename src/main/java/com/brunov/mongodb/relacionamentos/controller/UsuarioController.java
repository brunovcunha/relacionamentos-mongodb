package com.brunov.mongodb.relacionamentos.controller;

import com.brunov.mongodb.relacionamentos.models.Perfil;
import com.brunov.mongodb.relacionamentos.models.Postagem;
import com.brunov.mongodb.relacionamentos.models.Usuario;
import com.brunov.mongodb.relacionamentos.repositories.PerfilRepository;
import com.brunov.mongodb.relacionamentos.repositories.PostagemRepository;
import com.brunov.mongodb.relacionamentos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.EnumUtils;

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
        if(usuario.getPerfil() != null && usuario.getPerfil().getId() == null) {
            Perfil perfilSalvo = perfilRepository.save(usuario.getPerfil());

            usuario.setPerfil(perfilSalvo);
        }

        if (!usuario.getPostagens().isEmpty()) {
            List<Postagem> postagens = usuario.getPostagens().stream().map(postagem -> postagemRepository.save(postagem)).collect(Collectors.toList());
        }

        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable String id) {
        return usuarioRepository.findById(id).orElseThrow(() -> {throw new RuntimeException("Id não encontrado");});
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@RequestBody Usuario userAtualizado, @PathVariable String id) {
        Usuario usuario = getById(id);

        if (!userAtualizado.getNome().isEmpty()) {
            usuario.setNome(userAtualizado.getNome());
            usuarioRepository.save(usuario);
        }
        return usuario;
    }

}
