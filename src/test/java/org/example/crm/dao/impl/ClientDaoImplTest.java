package org.example.crm.dao.impl;

import org.example.crm.models.Client;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientDaoImplTest {
    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;
    private ClientDaoImpl clientDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        clientDao = new ClientDaoImpl();
        
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConn);
        }
        
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void addClient_Success_ReturnsTrue() throws SQLException {
        Client client = new Client("testId", "testName", "testHQ", "testPhone", "testEmail", "testAgentId");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = clientDao.addClient(client);

        assertTrue(result);
        verify(mockPreparedStatement).setString(1, "testId");
        verify(mockPreparedStatement).setString(2, "testName");
    }

    @Test
    void deleteClient_Success_ReturnsTrue() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = clientDao.deleteClient("testId");

        assertTrue(result);
        verify(mockPreparedStatement).setString(1, "testId");
    }
}
