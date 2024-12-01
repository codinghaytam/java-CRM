package org.example.crm.util;

import java.util.UUID;

public class GenerateUUID {
    public static void main(String[] args) {
        // Générer un UUID
        UUID carteDeFideliteId = UUID.randomUUID();

        // Afficher l'UUID généré
        System.out.println("ID de la carte de fidélité généré : " + carteDeFideliteId.toString());
    }
}