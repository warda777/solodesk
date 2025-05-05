package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.Model;
import com.warda.solodesk.Models.Payment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentsController implements Initializable {

    @FXML private TableView<Payment> payments_table;
    @FXML private TableColumn<Payment, Integer> col_id;
    @FXML private TableColumn<Payment, String> col_client;
    @FXML private TableColumn<Payment, String> col_date;
    @FXML private TableColumn<Payment, Double> col_amount;
    @FXML private TableColumn<Payment, String> col_method;
    @FXML private TableColumn<Payment, String> col_note;
    @FXML private Button add_payment_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumns();
        loadPaymentsFromDatabase();

        add_payment_btn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/warda/solodesk/Fxml/Main/AddPayment.fxml"));
                Parent root = loader.load();

                AddPaymentController controller = loader.getController();
                controller.setParentController(this); // WICHTIG!

                Stage stage = new Stage();
                stage.setTitle("Neue Zahlung");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void loadPaymentsFromDatabase() {
        ObservableList<Payment> payments = Model.getInstance().getDatabaseDriver().fetchAllPayments();
        ObservableList<Payment> paidInvoices = Model.getInstance().getDatabaseDriver().fetchPaidInvoicesAsPayments();

        payments.addAll(paidInvoices);
        payments_table.setItems(payments);
    }

    private void initColumns() {
        col_id.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        col_client.setCellValueFactory(cell -> cell.getValue().clientProperty());
        col_date.setCellValueFactory(cell -> cell.getValue().dateProperty());
        col_amount.setCellValueFactory(cell -> cell.getValue().amountProperty().asObject());
        col_method.setCellValueFactory(cell -> cell.getValue().methodProperty());
        col_note.setCellValueFactory(cell -> cell.getValue().noteProperty());
    }
}
