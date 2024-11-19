package com.brunov.mongodb.relacionamentos.repositories;

import com.brunov.mongodb.relacionamentos.models.Estudante;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EstudanteRepository extends MongoRepository<Estudante, String> {
}
