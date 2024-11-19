package com.brunov.mongodb.relacionamentos.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "postagens")
public class Postagem {

    @Id
    private String id;
    private String titulo;
    private String conteudo;
}
