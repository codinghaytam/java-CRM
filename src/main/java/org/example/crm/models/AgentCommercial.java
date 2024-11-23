package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgentCommercial extends Employe{
    public AgentCommercial(String cne, String nom, String prenom, String password) {
        super(cne, nom, prenom, password);
    }
}
