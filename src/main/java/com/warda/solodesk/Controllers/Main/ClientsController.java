package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.Model;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.warda.solodesk.Models.Client;
import javafx.stage.Stage;

public class ClientsController implements Initializable {

    @FXML
    private TableView<Client> clients_table;
    @FXML
    private TableColumn<Client, Integer> col_id;
    @FXML
    private TableColumn<Client, String> col_firstname;
    @FXML
    private TableColumn<Client, String> col_lastname;
    @FXML
    private TableColumn<Client, String> col_username;
    @FXML
    private TableColumn<Client, String> col_joined;
    @FXML
    private Button add_client_btn;
    @FXML
    private TableColumn<Client, String> col_company;
    @FXML
    private TableColumn<Client, String> col_phone;
    @FXML
    private TableColumn<Client, String> col_email;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumns();
        loadClientData();

        add_client_btn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/warda/solodesk/Fxml/Main/AddClient.fxml"));
                Parent root = loader.load();

                AddClientController controller = loader.getController();
                controller.setParentController(this); // Verbindung herstellen

                Stage stage = new Stage();
                stage.setTitle("Neuen Kunden hinzufügen");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_joined.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        col_company.setCellValueFactory(new PropertyValueFactory<>("company"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadClientData() {
        ObservableList<Client> clientList = Model.getInstance().getDatabaseDriver().fetchAllClients();
        clients_table.setItems(clientList);
        clients_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void loadClientsFromDatabase() {
        ObservableList<Client> clients = Model.getInstance().getDatabaseDriver().fetchAllClients();
        clients_table.setItems(clients);
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
