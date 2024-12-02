package org.example.crm.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.TreeMap;

@Setter
@Getter
@ToString
public class LoyaltyCard {
    String carteDeFideliteId;
    Map<String,Double> categoryDiscount;
    public LoyaltyCard(String cardId) {
        this.carteDeFideliteId = cardId;
        this.categoryDiscount = new TreeMap<>();
    }
}
