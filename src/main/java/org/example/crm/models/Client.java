package org.example.crm.models;

import java.util.List; 

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Client extends Lead {
    LoyaltyCard loyaltyCard;
    public Client(String entrepriseId, String entrepriseName, String headquarters, String phone, String email) {
        super(entrepriseId, entrepriseName, headquarters, phone, email);
    }
}