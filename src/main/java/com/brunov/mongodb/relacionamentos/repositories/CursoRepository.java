package com.brunov.mongodb.relacionamentos.repositories;

import com.brunov.mongodb.relacionamentos.models.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CursoRepository extends MongoRepository<Curso, String> {
}
