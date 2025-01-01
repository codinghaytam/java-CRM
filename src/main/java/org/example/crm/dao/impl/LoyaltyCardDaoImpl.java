package org.example.crm.dao.impl;

import org.example.crm.dao.LoyaltyCardDao;
import javafx.util.Pair;
import org.example.crm.models.LoyaltyCard;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public class LoyaltyCardDaoImpl implements LoyaltyCardDao{

    private Connection connection;

    public LoyaltyCardDaoImpl() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    @Override
    public Map<String,Double> setDiscounts(List<Pair<String,Double>> discounts){
        Map<String,Double> categoryDiscount = new TreeMap<>();
        for(Pair<String,Double> discount : discounts){
            categoryDiscount.put(discount.getKey(), discount.getValue());
        }
        return categoryDiscount;
    }
    @Override
    public void save(LoyaltyCard loyaltyCard, String entrepriseId) throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "INSERT INTO cartedefidelite (carteDeFideliteId,entrepriseId,statut) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, loyaltyCard.getCarteDeFideliteId());
            statement.setString(2, entrepriseId);
            statement.setString(3,"suspendue");

            statement.executeUpdate();
        }
    }

    @Override
    public LoyaltyCard findById(String carteDeFideliteId) throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "SELECT carteDeFideliteId FROM carteDeFidelite WHERE carteDeFideliteId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carteDeFideliteId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new LoyaltyCard(resultSet.getString("carteDeFideliteId"));
                }
            }
        }
        return null;
    }

    @Override
    public void updateStatus(String carteDeFideliteId, String status) throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "UPDATE carteDeFidelite SET statut = ? WHERE carteDeFideliteId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setString(2, carteDeFideliteId);
            statement.executeUpdate();
        }
    }

    @Override
    public List<LoyaltyCard> findAll() throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "SELECT carteDeFideliteId FROM carteDeFidelite";
        List<LoyaltyCard> loyaltyCards = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                LoyaltyCard card = new LoyaltyCard(resultSet.getString("carteDeFideliteId"));
                loyaltyCards.add(card);
            }
        }
        return loyaltyCards;
    }

    @Override
    public void delete(String carteDeFideliteId) throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "DELETE FROM carteDeFidelite WHERE carteDeFideliteId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carteDeFideliteId);
            statement.executeUpdate();
        }
    }

    @Override
    public void saveDiscount(String carteDeFideliteId, String categorieId, double remisePourcentage) throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "INSERT INTO remises (carteDeFideliteId, categorieId, remisePourcentage) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carteDeFideliteId);
            statement.setString(2, categorieId);
            statement.setDouble(3, remisePourcentage);
            statement.executeUpdate();
        }
    }

    @Override
    public Map<String, Double> getDiscounts(String carteDeFideliteId) throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "SELECT categorieId, remisePourcentage FROM remises WHERE carteDeFideliteId = ?";
        Map<String, Double> discounts = new TreeMap<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carteDeFideliteId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    discounts.put(resultSet.getString("categorieId"), resultSet.getDouble("remisePourcentage"));
                }
            }
        }
        return discounts;
    }
    public LoyaltyCard findByOwner(String entrepriseId) throws SQLException {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "SELECT carteDeFideliteId FROM carteDeFidelite WHERE entrepriseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entrepriseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new LoyaltyCard(resultSet.getString("carteDeFideliteId"));
                }
            }
        }
        return null;
    }
}