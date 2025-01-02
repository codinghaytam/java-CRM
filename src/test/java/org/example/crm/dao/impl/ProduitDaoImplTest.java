package org.example.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.example.crm.models.Produit;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class ProduitDaoImplTest {
    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;
    private ProduitDaoImpl produitDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        produitDao = new ProduitDaoImpl();
        
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConn);
        }
        
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void selectAll_Success_ReturnsList() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("produitId")).thenReturn("id1", "id2");
        when(mockResultSet.getString("produitNom")).thenReturn("nom1", "nom2");
        when(mockResultSet.getString("produitsCategorie")).thenReturn("cat1", "cat2");
        when(mockResultSet.getFloat("prix")).thenReturn(100.0f, 200.0f);

        List<Produit> result = produitDao.selectAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("id1", result.get(0).getProduitId());
        assertEquals("nom2", result.get(1).getProduitNom());
    }
}
