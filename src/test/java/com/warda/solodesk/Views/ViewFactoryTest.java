package com.warda.solodesk.Views;

import com.warda.solodesk.Models.Model;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ViewFactoryIntegrationTest {

    @BeforeAll
    static void initJavaFxToolkit() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("JavaFX Toolkit nicht gestartet");
        }
    }

    @BeforeEach
    void injectTestPane() {
        BorderPane pane = new BorderPane();
        Model.getInstance().setMainParent(pane);
    }

    @Test
    void setMainCenter_loadsDummyFxml_andSetsCenter() {
        ViewFactory vf = new ViewFactory();
        // Pfad zu unserer Test-FXML (muss exakt so im resources-Ordner liegen)
        vf.setMainCenter("/Fxml/dummy.fxml");

        BorderPane mainPane = Model.getInstance().getMainParent();
        assertNotNull(mainPane.getCenter(), "Center darf nicht null sein");
        assertTrue(mainPane.getCenter() instanceof BorderPane,
                "Center muss ein BorderPane aus dummy.fxml sein");
    }
}
