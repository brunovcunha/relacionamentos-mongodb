package com.brunov.mongodb.relacionamentos.controller;

import com.brunov.mongodb.relacionamentos.models.Perfil;
import com.brunov.mongodb.relacionamentos.repositories.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {
    private final PerfilRepository perfilRepository;


    @PostMapping
    public Perfil create(@RequestBody Perfil perfil) {
        if (!perfil.getId().isEmpty()) {
            return perfilRepository.save(perfil);
        } else {
            throw new RuntimeException("Usuario vazio");
        }
    }

    @GetMapping("/{id}")
    public Perfil getPerfilById(@PathVariable String id) {
        return perfilRepository.findById(id).orElseThrow(() -> {throw new RuntimeException("Perfil não encontrado");});
    }

    @GetMapping
    public List<Perfil> listarPerfis() {
         return perfilRepository.findAll();
    }

    @PutMapping("/{id}")
    public Perfil atualizarPerfil(@RequestBody Perfil perfilAtualizado, @PathVariable String id) {
        Perfil perfil = getPerfilById(id);

        if (perfil.getId().equals(id)) {
            return perfilRepository.save(perfil);
        } else {
            throw new RuntimeException("Perfil não encontrado!");
        }
    }



}
