package org.example.crm.models;


import lombok.AllArgsConstructor; 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class Supervisor extends Employe{
    List<AgentCommercial> agents;
    public Supervisor(String cne, String nom, String prenom, String password) {
        super(cne, nom, prenom, password);
    }
}