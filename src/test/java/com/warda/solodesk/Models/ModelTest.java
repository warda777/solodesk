package com.warda.solodesk.Models;

import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    void testSingletonInstanceIsNotNull() {
        Model instance = Model.getInstance();
        assertNotNull(instance);
    }

    @Test
    void testSingletonReturnsSameInstance() {
        Model instance1 = Model.getInstance();
        Model instance2 = Model.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testSetAndGetMainParent() {
        Model model = Model.getInstance();
        BorderPane pane = new BorderPane();
        model.setMainParent(pane);
        assertEquals(pane, model.getMainParent());
    }

    @Test
    void testCheckLoginWithCorrectCredentials() {
        Model model = Model.getInstance();
        boolean result = model.checkLogin("admin", "123456");
        assertTrue(result); // funktioniert nur, wenn diese Zugangsdaten wirklich in der DB stehen
    }

    @Test
    void testCheckLoginWithWrongCredentials() {
        Model model = Model.getInstance();
        boolean result = model.checkLogin("wrong", "wrong");
        assertFalse(result);
    }
}
