package org.example.crm.dao.impl;

import org.example.crm.models.LoyaltyCard;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import java.sql.*;
import static org.mockito.Mockito.*;


public class LoyaltyCardDaoImplTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    
    private LoyaltyCardDaoImpl loyaltyCardDao;

    @BeforeEach
    void setUp() throws SQLException {
        when(DatabaseConnection.getConnection()).thenReturn(mockConnection);
        loyaltyCardDao = new LoyaltyCardDaoImpl();
    }

    @Test
    public void testSave() throws SQLException {
        LoyaltyCard card = new LoyaltyCard("CARD123");
        String entrepriseId = "ENT123";
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        assertDoesNotThrow(() -> loyaltyCardDao.save(card, entrepriseId));
        verify(mockPreparedStatement).setString(1, card.getCarteDeFideliteId());
        verify(mockPreparedStatement).setString(2, entrepriseId);
    }

    @Test
    public void testFindById() throws SQLException {
        String cardId = "CARD123";
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("carteDeFideliteId")).thenReturn(cardId);
        
        LoyaltyCard card = loyaltyCardDao.findById(cardId);
        assertNotNull(card);
        assertEquals(cardId, card.getCarteDeFideliteId());
    }
}