package com.warda.solodesk.Models;

import javafx.beans.property.*;

public class Invoice {
    private final IntegerProperty id;
    private final StringProperty client;
    private final StringProperty date;
    private final DoubleProperty amount;
    private final StringProperty status;
    private final StringProperty description;

    // Konstruktor mit allen Parametern
    public Invoice(int id, String client, String date, double amount, String status, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.client = new SimpleStringProperty(client);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleDoubleProperty(amount);
        this.status = new SimpleStringProperty(status);
        this.description = new SimpleStringProperty(description);
    }

    // Getter-Properties für TableView-Bindung
    public IntegerProperty idProperty() { return id; }
    public StringProperty clientProperty() { return client; }
    public StringProperty dateProperty() { return date; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty statusProperty() { return status; }
    public StringProperty descriptionProperty() { return description; }

    // Optionale Getter für manuelle Abfragen
    public int getId() { return id.get(); }
    public String getClient() { return client.get(); }
    public String getDate() { return date.get(); }
    public double getAmount() { return amount.get(); }
    public String getStatus() { return status.get(); }
    public String getDescription() { return description.get(); }
}


