package org.example.crm.util;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class DatabaseConnectionTest {
    @Test
    public void testGetConnection() {
        assertDoesNotThrow(() -> {
            var connection = DatabaseConnection.getConnection();
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        });
    }
    
    @Test
    public void testConnectionSingleton() throws SQLException {
        var connection1 = DatabaseConnection.getConnection();
        var connection2 = DatabaseConnection.getConnection();
        assertSame(connection1, connection2);
    }
}