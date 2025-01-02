package org.example.crm.dao.impl;

import org.example.crm.models.Categorie;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import java.sql.*;
import static org.mockito.Mockito.*;

class CategorieDaoImplTest {
    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;
    private CategorieDaoImpl categorieDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConn);
        }
        
        categorieDao = new CategorieDaoImpl();
    }

    @Test
    void addCategorie_Success() throws Exception {
        Categorie categorie = new Categorie("testId", "testNom");
        when(mockConn.isClosed()).thenReturn(false);
        
        categorieDao.addCategorie(categorie);
        
        verify(mockPreparedStatement).setString(1, "testId");
        verify(mockPreparedStatement).setString(2, "testNom");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void getCategorieById_ExistingCategorie_ReturnsCategorie() throws Exception {
        when(mockConn.isClosed()).thenReturn(false);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("categorieId")).thenReturn("testId");
        when(mockResultSet.getString("nomCategorie")).thenReturn("testNom");

        Categorie result = categorieDao.getCategorieById("testId");

        assertNotNull(result);
        assertEquals("testId", result.getId());
        assertEquals("testNom", result.getNom());
    }
}