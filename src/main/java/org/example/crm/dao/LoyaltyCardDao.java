package org.example.crm.dao;

import javafx.util.Pair;
import java.util.List;
import java.util.Map;

public interface LoyaltyCardDao {
    public Map<String,Double> setDiscounts(List<Pair<String,Double>> discounts);
}
