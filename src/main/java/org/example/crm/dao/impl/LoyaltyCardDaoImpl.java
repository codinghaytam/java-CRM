package org.example.crm.dao.impl;

import org.example.crm.dao.LoyaltyCardDao;

import javafx.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LoyaltyCardDaoImpl implements LoyaltyCardDao{
    @Override
    public Map<String,Double> setDiscounts(List<Pair<String,Double>> discounts){
        Map<String,Double> categoryDiscount = new TreeMap<>();
        for(Pair<String,Double> discount : discounts){
            categoryDiscount.put(discount.getKey(), discount.getValue());
        }
        return categoryDiscount;
    }
}