package com.warda.solodesk.Models;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseDriverTest {

    private DatabaseDriver driver;
    private Connection mockConn;

    @BeforeEach
    void setUp() throws Exception {
        // Erstelle echte Instanz, aber injiziere unser Mock-Connection
        driver = new DatabaseDriver();

        mockConn = mock(Connection.class);
        Field connField = DatabaseDriver.class.getDeclaredField("conn");
        connField.setAccessible(true);
        connField.set(driver, mockConn);
    }

    @Test
    void validateLogin_returnsTrueWhenResultSetHasRow() throws Exception {
        String user = "u", pw = "p";
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);

        assertTrue(driver.validateLogin(user, pw));

        verify(mockConn).prepareStatement("SELECT * FROM Admins WHERE Username = ? AND Password = ?");
        verify(mockStmt).setString(1, user.trim());
        verify(mockStmt).setString(2, pw.trim());
        verify(mockStmt).executeQuery();
        verify(mockRs).next();
    }

    @Test
    void validateLogin_returnsFalseWhenNoRow() throws Exception {
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        assertFalse(driver.validateLogin("x","y"));
    }

    @Test
    void validateLogin_returnsFalseWhenConnNull() throws Exception {
        // setze conn auf null
        Field connField = DatabaseDriver.class.getDeclaredField("conn");
        connField.setAccessible(true);
        connField.set(driver, null);

        assertFalse(driver.validateLogin("a","b"));
    }

    @Test
    void getClientCount_readsCountFromResultSet() throws Exception {
        Statement mockStmt = mock(Statement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery("SELECT COUNT(*) AS Total FROM Clients")).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("Total")).thenReturn(42);

        int count = driver.getClientCount();
        assertEquals(42, count);

        verify(mockConn).createStatement();
        verify(mockStmt).executeQuery("SELECT COUNT(*) AS Total FROM Clients");
    }

    @Test
    void getClientCount_returnsZeroOnError() throws Exception {
        // Simuliere SQLException bei createStatement()
        when(mockConn.createStatement()).thenThrow(new SQLException("DB down"));

        // Act + Assert
        assertEquals(0, driver.getClientCount());
    }

    @Test
    void insertClient_returnsTrueOnSuccess() throws Exception {
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        when(mockConn.prepareStatement(startsWith("INSERT INTO Clients"))).thenReturn(mockStmt);

        boolean result = driver.insertClient("John","Doe","jdoe","Acme","123","j@x");
        assertTrue(result);

        verify(mockStmt).setString(1, "John");
        verify(mockStmt).setString(2, "Doe");
        verify(mockStmt).setString(3, "jdoe");
        verify(mockStmt).setString(4, "Acme");
        verify(mockStmt).setString(5, "123");
        verify(mockStmt, times(7)).setString(anyInt(), anyString());
        verify(mockStmt).executeUpdate();
    }

    @Test
    void insertClient_returnsFalseOnException() throws Exception {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException());
        assertFalse(driver.insertClient("a","b","c","d","e","f"));
    }

    @Test
    void fetchAllClients_buildsObservableList() throws Exception {
        Statement mockStmt = mock(Statement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery("SELECT * FROM Clients")).thenReturn(mockRs);
        // Simuliere zwei Zeilen
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("ID")).thenReturn(1, 2);
        when(mockRs.getString("Vorname")).thenReturn("A","B");
        when(mockRs.getString("Name")).thenReturn("X","Y");
        when(mockRs.getString("Username")).thenReturn("u1","u2");
        when(mockRs.getString("Firma")).thenReturn("f1","f2");
        when(mockRs.getString("Telefon")).thenReturn("t1","t2");
        when(mockRs.getString("Email")).thenReturn("e1","e2");
        when(mockRs.getString("Erstellt am")).thenReturn("01.01.2020","02.02.2021");

        ObservableList<com.warda.solodesk.Models.Client> list = driver.fetchAllClients();
        assertEquals(2, list.size());
        assertEquals("A", list.get(0).getFirstName());
        assertEquals("Y", list.get(1).getLastName());
    }
}
