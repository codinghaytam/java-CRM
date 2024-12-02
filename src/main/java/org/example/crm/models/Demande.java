package org.example.crm.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Demande {
    String demandeId;
    Client client;
    String status;
    public Demande(Client client, Status status){
        this.client = client;
        this.status = status.name();
    }
}
