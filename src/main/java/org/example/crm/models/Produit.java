package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
class Produit {
    String produitId;
    String produitTitle;
    String categorieId;
    float prix;
}
