package org.example.crm.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.example.crm.models.Request;
import org.example.crm.models.statut;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

public class RequestDaoImplTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    
    private RequestDaoImpl requestDao;

    @BeforeEach
    void setUp() {
        requestDao = new RequestDaoImpl();
    }

    @Test
    public void testShowRequests() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, false);
            
            List<Request> requests = requestDao.showRequests();
            assertNotNull(requests);
        }
    }

    @Test
    public void testAddRequest() throws SQLException {
        Request request = new Request();
        request.leadIdProperty().set("LEAD123");
        request.agentIdProperty().set("AGENT123");
        request.loyaltyCardIdProperty().set("CARD123");
        request.statusProperty().set(statut.PENDING);
        request.creationDateProperty().set(new Date());
        request.descriptionProperty().set("Test request");
        
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            
            boolean result = requestDao.addRequest(request);
            assertTrue(result);
            
            verify(mockPreparedStatement).setString(1, request.getLeadId());
            verify(mockPreparedStatement).setString(2, request.getAgentId());
        }
    }
    
}
