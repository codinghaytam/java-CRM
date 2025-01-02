package org.example.crm.dao.impl;

import org.example.crm.models.AgentCommercial;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;

public class SupervisorDaoImplTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    
    private SupervisorDaoImpl supervisorDao;

    @BeforeEach
    void setUp() {
        supervisorDao = new SupervisorDaoImpl();
    }

    @Test
    public void testAddAgent() throws SQLException {
        AgentCommercial agent = new AgentCommercial("CNE123", "Nom", "Prenom", "password", "SUP123");
        
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            
            boolean result = supervisorDao.addAgent(agent);
            assertTrue(result);
            
            verify(mockPreparedStatement).setString(1, agent.getCNE());
            verify(mockPreparedStatement).setString(2, agent.getNom());
            verify(mockPreparedStatement).setString(3, agent.getPrenom());
            verify(mockPreparedStatement).setString(5, agent.getSupervisor_id());
        }
    }

    @Test
    public void testShowAgents() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, false);
            when(mockResultSet.getString("CNE")).thenReturn("CNE123");
            
            List<AgentCommercial> agents = supervisorDao.showAgents();
            assertFalse(agents.isEmpty());
            assertEquals("CNE123", agents.get(0).getCNE());
        }
    }
}