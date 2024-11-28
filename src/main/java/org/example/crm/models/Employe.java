package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employe {
    protected String CNE;
    protected String nom;
    protected String prenom;
    protected String password;
}