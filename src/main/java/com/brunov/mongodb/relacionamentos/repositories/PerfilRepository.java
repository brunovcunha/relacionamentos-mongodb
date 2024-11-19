package com.brunov.mongodb.relacionamentos.repositories;

import com.brunov.mongodb.relacionamentos.models.Perfil;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PerfilRepository extends MongoRepository<Perfil, String> {
}
