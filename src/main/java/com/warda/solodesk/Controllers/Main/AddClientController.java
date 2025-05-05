package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.DatabaseDriver;
import com.warda.solodesk.Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddClientController {

    private ClientsController parentController;

    public void setParentController(ClientsController controller) {
        this.parentController = controller;
    }

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField companyField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;

    @FXML
    private void onSave() {
        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String company = companyField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (first.isEmpty() || last.isEmpty() || username.isEmpty()) {
            showAlert("Vorname, Nachname und Benutzername sind Pflichtfelder.");
            return;
        }

        DatabaseDriver db = Model.getInstance().getDatabaseDriver();
        boolean success = db.insertClient(first, last, username, company, phone, email);

        if (success) {
            showAlert("Kunde erfolgreich gespeichert.");

            if (parentController != null) {
                parentController.loadClientsFromDatabase();
            }

            ((Stage) firstNameField.getScene().getWindow()).close();
        } else {
            showAlert("Fehler beim Speichern.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hinweis");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
