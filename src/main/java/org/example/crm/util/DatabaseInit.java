package org.example.crm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInit {
    static public boolean Initialize_Database()throws SQLException {
        // Connect to the database
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pr=null;
        boolean is_init=true;


        pr = conn.prepareStatement("CREATE table IF NOT EXISTS Remise (id varchar(255) PRIMARY KEY," +
                "remise double"+
                " );");
        try{
            is_init&=pr.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        // Create table if not exists client
            pr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Client (" +
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "nom VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "telephone VARCHAR(20), " +
                    "adresse VARCHAR(255), " +
                    "creation_date TIMESTAMP);");
        try{
            is_init&=pr.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //produit
            pr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Produit (" +
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "nom VARCHAR(255), " +
                    "description TEXT, " +
                    "image VARCHAR(255), " +
                    "quantite INT, " +
                    "prix DOUBLE);");
        try{
            is_init =pr.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }
        //
            pr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Command (" +
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "prixTotal DOUBLE, " +
                    "creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
        try{
            is_init&=pr.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }
        //leads
            pr = conn.prepareStatement("CREATE table IF NOT EXISTS Leads (id varchar(255) PRIMARY KEY,nom varchar(255),email varchar(255),adresse varchar(255),telephone varchar(255),status varchar(255),creation_date TimeStamp DEFAULT CURRENT_TIMESTAMP,update_date timestamp DEFAULT CURRENT_TIMESTAMP);");
        try{
            is_init&=pr.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
            // CarteFidelite
            pr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS CarteFidelite (" +
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "carte VARCHAR(255), " +
                    "valReduction DOUBLE, " +
                    "clientId VARCHAR(255), " +
                    "remiseId VARCHAR(255), " +
                    "FOREIGN KEY (clientId) REFERENCES Client(id)," +
                    "FOREIGN KEY (remiseId) REFERENCES Remise(id));");
            try {
                is_init &= pr.execute();
            } catch (SQLException e) {
                System.err.println("Error creating CarteFidelite table: " + e.getMessage());
                is_init = false;
            }

            // ProduitCommander table
            pr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ProduitCommander (" +
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "quantite INT, " +
                    "produitId VARCHAR(255), " +
                    "commandId VARCHAR(255), " +
                    "FOREIGN KEY (produitId) REFERENCES Produit(id), " +
                    "FOREIGN KEY (commandId) REFERENCES Command(id));");
            try {
                is_init &= pr.execute();
            } catch (SQLException e) {
                System.err.println("Error creating ProduitCommander table: " + e.getMessage());
                is_init = false;
            }


            // Supervisor table
            pr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Supervisor (" +
                    "CNE VARCHAR(10) PRIMARY KEY, " +
                    "nom VARCHAR(50) NOT NULL, " +
                    "prenom VARCHAR(50) NOT NULL, " +
                    "password VARCHAR(255) NOT NULL);");
            try {
                is_init &= pr.execute();
            } catch (SQLException e) {
                is_init = false;
            }
        // agent_commercial table
        pr = conn.prepareStatement("CREATE TABLE agent_commercial (" +
                "    CNE VARCHAR(10) PRIMARY KEY,        " +
                "    nom VARCHAR(50) NOT NULL,           " +
                "    prenom VARCHAR(50) NOT NULL,        " +
                "    password VARCHAR(255) NOT NULL,  " +
                "    supervisor_CNE VARCHAR(10),         " +
                "    FOREIGN KEY (supervisor_CNE) REFERENCES Supervisor(CNE)  " +
                ");");
        try {
            is_init &= pr.execute();
        } catch (SQLException e) {
            System.err.println("Error creating agent_commercial table: " + e.getMessage());
            is_init = false;
        }
        // demande
        pr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Demande (" +
                "    id VARCHAR(10) PRIMARY KEY," +
                "    status VARCHAR(50) NOT NULL," +
                "    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "    supervisor_CNE VARCHAR(20)," +
                "    leadID VARCHAR(10)," +
                "    carteId VARCHAR(10), " +
                "    FOREIGN KEY (supervisor_CNE) REFERENCES Supervisor(CNE)," +
                "    FOREIGN KEY (leadID) REFERENCES Leads(id)," +
                "    FOREIGN KEY (carteId) REFERENCES CarteFidelite(id)" +
                ");");
        try {
            is_init &= pr.execute();
        } catch (SQLException e) {
            System.err.println("Error creating agent_commercial table: " + e.getMessage());
            is_init = false;
        }


        // categorie
        pr = conn.prepareStatement("CREATE table IF NOT EXISTS Categorie (" +
                "id varchar(255) PRIMARY KEY," +
                "nom varchar(255)," +
                "categorield varchar(255)," +
                "remiseId varchar(255)," +
                "FOREIGN KEY (remiseId) REFERENCES Remise(id));");
        try{
            is_init&=pr.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        pr = conn.prepareStatement("ALTER TABLE client ADD employeeID varchar(10);");
        try{
            is_init&=pr.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        pr = conn.prepareStatement("ALTER TABLE client " +
                "ADD CONSTRAINT fk_employeid " +
                "FOREIGN KEY (employeeid) REFERENCES Agentcommercial(CNE);");
        try{
            is_init&=pr.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return is_init;
    }
}
