package com.brunov.mongodb.relacionamentos.repositories;

import com.brunov.mongodb.relacionamentos.models.Perfil;
import com.brunov.mongodb.relacionamentos.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
