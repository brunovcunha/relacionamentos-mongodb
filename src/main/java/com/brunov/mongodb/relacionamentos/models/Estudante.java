package com.brunov.mongodb.relacionamentos.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "estudantes")
public class Estudante {
    @Id
    private String id;
    private String nome;

    @DBRef
    private List<Curso> cursos;

}
