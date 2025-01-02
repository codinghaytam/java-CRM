package org.example.crm.dao.impl;
import org.example.crm.models.Lead;
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

class LeadDaoImplTest {
    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private Statement mockStatement;
    @Mock private ResultSet mockResultSet;
    private LeadDaoImpl leadDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        leadDao = new LeadDaoImpl();
        
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConn);
        }
        
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConn.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @Test
    void ajoutLead_Success_ReturnsTrue() throws SQLException {
        Lead lead = new Lead("testId", "testName", "testHQ", "testPhone", "testEmail", "testAgent");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = leadDao.ajoutLead(lead);

        assertTrue(result);
        verify(mockPreparedStatement).setString(1, "testId");
        verify(mockPreparedStatement).setString(2, "testName");
    }

    @Test
    void afficheLead_Success_ReturnsList() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("leadId")).thenReturn("testId");
        when(mockResultSet.getString("Name")).thenReturn("testName");
        when(mockResultSet.getString("headquarters")).thenReturn("testHQ");
        when(mockResultSet.getString("phone")).thenReturn("testPhone");
        when(mockResultSet.getString("email")).thenReturn("testEmail");
        when(mockResultSet.getString("agentId")).thenReturn("testAgent");

        List<Lead> result = leadDao.afficheLead();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testId", result.get(0).getLeadId());
    }

    @Test
    void deleteLead_Success_ReturnsTrue() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = leadDao.deleteLead("testId");

        assertTrue(result);
        verify(mockPreparedStatement).setString(1, "testId");
    }
}