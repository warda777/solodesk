package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML private Button dashboard_btn;
    @FXML private Button clients_btn;
    @FXML private Button invoices_btn;
    @FXML private Button payments_btn;
    @FXML private Button profile_btn;
    @FXML private Button logout_btn;

    @FXML
    public void initialize() {

        dashboard_btn.setOnAction(e ->
                Model.getInstance().getViewFactory().setMainCenter("/com/warda/solodesk/Fxml/Main/Dashboard.fxml"));
        clients_btn.setOnAction(e -> {
            Model.getInstance().getViewFactory().setMainCenter("/com/warda/solodesk/Fxml/Main/Clients.fxml");
        });
        invoices_btn.setOnAction(event ->
                Model.getInstance().getViewFactory().setMainCenter("/com/warda/solodesk/Fxml/Main/Invoices.fxml")
        );
        payments_btn.setOnAction(e ->
                Model.getInstance().getViewFactory().setMainCenter("/com/warda/solodesk/Fxml/Main/Payments.fxml")
        );
        profile_btn.setOnAction(event ->
                Model.getInstance().getViewFactory().showProfileView());
        logout_btn.setOnAction(e -> {
            // Aktuelles Fenster schließen
            Stage stage = (Stage) logout_btn.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showLoginWindow();
        });
    }
}
