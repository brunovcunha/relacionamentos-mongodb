package com.brunov.mongodb.relacionamentos.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "perfis")
public class Perfil {
    @Id
    private String id;
    private String bio;
    private String avatarUri;


}
