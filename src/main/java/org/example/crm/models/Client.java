package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Client {
    String entreprise;
    String entrepriseId;
    String headquarters;
    String phone;
    String email;
    LoyaltyCard loyaltyCard;
}