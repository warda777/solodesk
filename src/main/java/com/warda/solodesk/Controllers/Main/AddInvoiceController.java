package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.DatabaseDriver;
import com.warda.solodesk.Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddInvoiceController {
    private InvoicesController parentController;

    public void setParentController(InvoicesController controller) {
        this.parentController = controller;
    }

    @FXML private ComboBox<String> clientCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private TextField descriptionField;

    @FXML
    public void initialize() {
        // Alle Kundennamen laden
        clientCombo.getItems().addAll(Model.getInstance().getDatabaseDriver().getAllClientNames());
    }

    @FXML
    private void onSave() {
        String client = clientCombo.getValue(); // "Lara Neumann"
        LocalDate date = datePicker.getValue();
        String amountText = amountField.getText().trim();
        String status = statusCombo.getValue();
        String description = descriptionField.getText().trim();

        if (client == null || date == null || amountText.isEmpty() || status == null) {
            showAlert("Bitte fülle alle Pflichtfelder aus.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            DatabaseDriver db = Model.getInstance().getDatabaseDriver();
            int clientId = db.getClientIdByName(client);
            if (clientId == -1) {
                showAlert("Kunde konnte nicht gefunden werden.");
                return;
            }

            boolean success = db.insertInvoice(clientId, formattedDate, amount, status, description);

            if (success) {
                showAlert("Rechnung gespeichert.");
                ((Stage) clientCombo.getScene().getWindow()).close();
                if (parentController != null) {
                    parentController.loadInvoicesFromDatabase(); // UI aktualisieren
                }
            } else {
                showAlert("Fehler beim Speichern der Rechnung.");
            }

        } catch (NumberFormatException e) {
            showAlert("Bitte eine gültige Zahl für den Betrag eingeben.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fehler beim Speichern.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hinweis");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
