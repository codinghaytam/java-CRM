package org.example.crm.dao;

import javafx.util.Pair;
import org.example.crm.models.LoyaltyCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
public interface LoyaltyCardDao {
    public Map<String,Double> setDiscounts(List<Pair<String,Double>> discounts);

    void save(LoyaltyCard loyaltyCard, String entrepriseId) throws SQLException;

    LoyaltyCard findById(String carteDeFideliteId) throws SQLException;

    void updateStatus(String carteDeFideliteId, String status) throws SQLException;

    List<LoyaltyCard> findAll() throws SQLException;

    void delete(String carteDeFideliteId) throws SQLException;

    void saveDiscount(String carteDeFideliteId, String categorieId, double remisePourcentage) throws SQLException;

    Map<String, Double> getDiscounts(String carteDeFideliteId) throws SQLException;
}