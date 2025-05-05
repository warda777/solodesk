package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.Model;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.warda.solodesk.Models.Invoice;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InvoicesController implements Initializable {

    @FXML private TableView<Invoice> invoices_table;
    @FXML private TableColumn<Invoice, Integer> col_id;
    @FXML private TableColumn<Invoice, String> col_client;
    @FXML private TableColumn<Invoice, String> col_date;
    @FXML private TableColumn<Invoice, Double> col_amount;
    @FXML private TableColumn<Invoice, String> col_status;
    @FXML private TableColumn<Invoice, String> col_description;
    @FXML private Button add_invoice_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumns();
        loadInvoicesFromDatabase();

        add_invoice_btn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/warda/solodesk/Fxml/Main/AddInvoice.fxml"));
                Parent root = loader.load();

                AddInvoiceController controller = loader.getController();
                controller.setParentController(this);

                Stage stage = new Stage();
                stage.setTitle("Neue Rechnung");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void loadInvoicesFromDatabase() {
        System.out.println("Rechnungsliste neu geladen.");
        ObservableList<Invoice> invoices = Model.getInstance().getDatabaseDriver().fetchAllInvoices();
        invoices_table.setItems(invoices);
    }

    private void initColumns() {
        col_id.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        col_client.setCellValueFactory(cell -> cell.getValue().clientProperty());
        col_date.setCellValueFactory(cell -> cell.getValue().dateProperty());
        col_amount.setCellValueFactory(cell -> cell.getValue().amountProperty().asObject());
        col_status.setCellValueFactory(cell -> cell.getValue().statusProperty());
        col_description.setCellValueFactory(cell -> cell.getValue().descriptionProperty());

    }

}
