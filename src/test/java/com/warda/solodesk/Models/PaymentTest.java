package com.warda.solodesk.Models;

import javafx.beans.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment(
                42,
                "Max Mustermann",
                "2025-04-29",
                123.45,
                "Kreditkarte",
                "Test-Notiz"
        );
    }

    @Test
    void testIdProperty() {
        IntegerProperty idProp = payment.idProperty();
        assertNotNull(idProp);
        assertEquals(42, idProp.get());
    }

    @Test
    void testClientProperty() {
        StringProperty clientProp = payment.clientProperty();
        assertNotNull(clientProp);
        assertEquals("Max Mustermann", clientProp.get());
    }

    @Test
    void testDateProperty() {
        StringProperty dateProp = payment.dateProperty();
        assertNotNull(dateProp);
        assertEquals("2025-04-29", dateProp.get());
    }

    @Test
    void testAmountProperty() {
        DoubleProperty amountProp = payment.amountProperty();
        assertNotNull(amountProp);
        assertEquals(123.45, amountProp.get(), 0.0001);
    }

    @Test
    void testMethodProperty() {
        StringProperty methodProp = payment.methodProperty();
        assertNotNull(methodProp);
        assertEquals("Kreditkarte", methodProp.get());
    }

    @Test
    void testNoteProperty() {
        StringProperty noteProp = payment.noteProperty();
        assertNotNull(noteProp);
        assertEquals("Test-Notiz", noteProp.get());
    }
}
