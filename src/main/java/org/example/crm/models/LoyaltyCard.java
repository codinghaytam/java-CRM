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
    public LoyaltyCard(String carteDeFideliteId) {
        this.carteDeFideliteId = carteDeFideliteId;
        this.categoryDiscount = new TreeMap<>();
    }
}