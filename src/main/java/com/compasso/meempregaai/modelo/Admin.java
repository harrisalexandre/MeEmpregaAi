package com.compasso.meempregaai.modelo;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin extends Usuario{

    public Admin(String nome, String email, String senha) {
        super();
    }
}