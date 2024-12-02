package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Produit {
    String produitId;
    String produitNom;
    String categorieId;
    Float prix;
    Integer quantite;
}