package com.warda.solodesk.Controllers.Main;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import com.warda.solodesk.Models.Model;

public class MainController {

    // Zugriff auf das Haupt-Layout (Main.fxml)
    @FXML
    private BorderPane main_parent;

    @FXML
    public void initialize() {
        Model.getInstance().setMainParent(main_parent);
    }
}
