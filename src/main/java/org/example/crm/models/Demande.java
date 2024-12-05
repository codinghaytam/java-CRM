package org.example.crm.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.example.crm.Enums.DemandeStatus;

@Getter
@Setter
public class Demande {
    String demandeId;
    String leadId;
    String employeeId ;
    String carteDeFideliteId;
    DemandeStatus statut;
    LocalDateTime dateDeCreation ;
    public Demande(String leadId,String employeeId,String carteDeFideliteId) {
        this.demandeId = UUID.randomUUID().toString();
        this.leadId = leadId;
        this.employeeId = employeeId;
        this.carteDeFideliteId = carteDeFideliteId;
        this.statut = DemandeStatus.enAttente;
        dateDeCreation = LocalDateTime.now();
    }
}
