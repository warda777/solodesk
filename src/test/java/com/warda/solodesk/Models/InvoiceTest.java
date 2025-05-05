package com.warda.solodesk.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    @Test
    public void testInvoiceInitialization() {
        Invoice invoice = new Invoice(1, "Max Mustermann",
                "30.04.2025", 100.0, "offen",
                "Testrechnung");
        assertEquals(1, invoice.getId());
        assertEquals("Max Mustermann", invoice.getClient());
        assertEquals("30.04.2025", invoice.getDate());
        assertEquals(100.0, invoice.getAmount());
        assertEquals("offen", invoice.getStatus());
        assertEquals("Testrechnung", invoice.getDescription());
    }
}
