package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgentCommercial extends Employe{
    private String Supervisor_id;

    public AgentCommercial(String cne, String nom, String prenom, String password,String Supervisor_id) {
        super(cne, nom, prenom, password);
        this.Supervisor_id = Supervisor_id;
    }
}
