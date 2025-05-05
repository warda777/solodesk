package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.DatabaseDriver;
import com.warda.solodesk.Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddPaymentController {

    private PaymentsController parentController;

    public void setParentController(PaymentsController controller) {
        this.parentController = controller;
    }

    @FXML private ComboBox<String> clientCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> methodCombo;
    @FXML private TextField noteField;

    @FXML
    public void initialize() {
        // Lade alle Kundennamen in die ComboBox
        clientCombo.getItems().addAll(Model.getInstance().getDatabaseDriver().getAllClientNames());
    }

    @FXML
    private void onSave() {
        String clientName = clientCombo.getValue();
        LocalDate date = datePicker.getValue();
        String amountText = amountField.getText().trim();
        String method = methodCombo.getValue();
        String note = noteField.getText().trim();

        if (clientName == null || date == null || amountText.isEmpty() || method == null) {
            showAlert("Bitte alle Pflichtfelder ausfüllen.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            DatabaseDriver db = Model.getInstance().getDatabaseDriver();
            boolean success = db.insertPayment(clientName, formattedDate, amount, method, note);

            if (success) {
                showAlert("Zahlung erfolgreich gespeichert.");
                ((Stage) clientCombo.getScene().getWindow()).close();

                if (parentController != null) {
                    parentController.loadPaymentsFromDatabase();
                }
            } else {
                showAlert("Fehler beim Speichern.");
            }

        } catch (NumberFormatException e) {
            showAlert("Bitte einen gültigen Betrag eingeben.");
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
