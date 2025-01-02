package org.example.crm.dao.impl;

import org.example.crm.models.AgentCommercial;
import org.example.crm.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import java.sql.*;
import static org.mockito.Mockito.*;

class AgentCommercialDaoImplTest {
 @Mock private Connection mockConn;
 @Mock private PreparedStatement mockPreparedStatement;
 @Mock private ResultSet mockResultSet;
 private AgentCommercialDaoImpl agentDao;

 @BeforeEach
 void setUp() throws SQLException {
     MockitoAnnotations.openMocks(this);
     agentDao = new AgentCommercialDaoImpl();
     
     // Mock DatabaseConnection
     try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
         mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConn);
     }
     
     when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
     when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
 }

 @Test
 void verifyLogin_ValidCredentials_ReturnsTrue() throws SQLException {
     when(mockResultSet.next()).thenReturn(true);
     
     boolean result = agentDao.verifyLogin("testCNE", "testPass");
     
     assertTrue(result);
     verify(mockPreparedStatement).setString(1, "testCNE");
     verify(mockPreparedStatement).setString(2, "testPass");
 }

 @Test
 void verifyLogin_InvalidCredentials_ReturnsFalse() throws SQLException {
     when(mockResultSet.next()).thenReturn(false);
     
     boolean result = agentDao.verifyLogin("invalidCNE", "invalidPass");
     
     assertFalse(result);
 }

 @Test
 void getAgentByCNE_ExistingAgent_ReturnsAgent() throws SQLException {
     when(mockResultSet.next()).thenReturn(true);
     when(mockResultSet.getString("CNE")).thenReturn("testCNE");
     when(mockResultSet.getString("nom")).thenReturn("testNom");
     when(mockResultSet.getString("prenom")).thenReturn("testPrenom");
     when(mockResultSet.getString("password")).thenReturn("testPass");
     when(mockResultSet.getString("supervisor_CNE")).thenReturn("supCNE");

     AgentCommercial result = agentDao.getAgentByCNE("testCNE");

     assertNotNull(result);
     assertEquals("testCNE", result.getCNE());
     assertEquals("testNom", result.getNom());
 }

 @Test
 void getAgentByCNE_NonexistentAgent_ReturnsNull() throws SQLException {
     when(mockResultSet.next()).thenReturn(false);

     AgentCommercial result = agentDao.getAgentByCNE("nonexistentCNE");

     assertNull(result);
 }
}