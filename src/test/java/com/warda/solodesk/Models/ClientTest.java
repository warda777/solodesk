package com.warda.solodesk.Models;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testClientInitialization() {
        // Arrange
        LocalDate createdAt = LocalDate.of(2025, 4, 29);
        Client client = new Client(
                1,
                "Max",
                "Mustermann",
                "maxmuster",
                createdAt,
                "Musterfirma",
                "0123456789",
                "max@example.com"
        );

        // Assert
        assertEquals(1, client.getId());
        assertEquals("Max", client.getFirstName());
        assertEquals("Mustermann", client.getLastName());
        assertEquals("maxmuster", client.getUsername());
        assertEquals("29.04.2025", client.getCreatedAt());
        assertEquals("Musterfirma", client.getCompany());
        assertEquals("0123456789", client.getPhone());
        assertEquals("max@example.com", client.getEmail());
    }
}