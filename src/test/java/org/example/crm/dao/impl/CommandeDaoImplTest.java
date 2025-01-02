package org.example.crm.dao.impl;

import org.example.crm.models.AgentCommercial;
import org.example.crm.models.LoyaltyCard;
import org.example.crm.models.Produit;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import java.sql.*;
import java.util.List;
import static org.mockito.Mockito.*;

class CommandeDaoImplTest {
    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockPreparedStatement;
    private CommandeDaoImpl commandeDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        commandeDao = new CommandeDaoImpl();
        
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConn);
        }
        
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void ajoutCommande_Success_ReturnsTrue() throws SQLException {
        LoyaltyCard card = new LoyaltyCard("testCard");
        AgentCommercial agent = new AgentCommercial("testCNE", "testNom", "testPrenom", "testPass", "testSup");
        List<Produit> produits = List.of(new Produit("testId", "testNom", "cat1", 100.0f, 1));

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        
        boolean result = commandeDao.ajoutCommande(card, agent, produits);
        
        assertTrue(result);
    }
}

