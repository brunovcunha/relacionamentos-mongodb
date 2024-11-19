package com.brunov.mongodb.relacionamentos.repositories;

import com.brunov.mongodb.relacionamentos.models.Postagem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostagemRepository extends MongoRepository<Postagem, String> {
}
