package org.example.crm.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter

public class AgentCommercial extends Employe{
    private String Supervisor_id;
    List<Lead> leads;
    public AgentCommercial(String cne, String nom, String prenom, String password,String Supervisor_id) {
        super(cne, nom, prenom, password);
        this.Supervisor_id = Supervisor_id;
    }

    public String getCNE(){
        return CNE;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public String getPassword(){
        return password;
    }

    public String getSupervisor_id(){
        return Supervisor_id;
    }
}