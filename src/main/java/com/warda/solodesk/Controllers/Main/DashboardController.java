package com.warda.solodesk.Controllers.Main;

import com.warda.solodesk.Models.Model;
import com.warda.solodesk.Models.DatabaseDriver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Text user_name;
    @FXML private Label login_date;
    @FXML private Label client_count;
    @FXML private Label open_invoices;
    @FXML private Label monthly_income;
    @FXML private ListView<String> activity_listview;

    private DatabaseDriver db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = Model.getInstance().getDatabaseDriver();
        setWelcomeText();
        setSummaryInfo();
        loadRecentActivity();
    }

    private void setWelcomeText() {
        user_name.setText("Willkommen zurück!");
        login_date.setText("Heute: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    private void setSummaryInfo() {
        int clientCount = db.getClientCount();
        int unpaidInvoices = db.getUnpaidInvoiceCount();
        double income = db.getMonthlyIncome(LocalDate.now().getMonthValue());

        client_count.setText(String.valueOf(clientCount));
        open_invoices.setText(String.valueOf(unpaidInvoices));
        monthly_income.setText(String.format("%.2f €", income));
    }

    private void loadRecentActivity() {
        activity_listview.getItems().addAll(db.getRecentActivities(5));
    }
}
