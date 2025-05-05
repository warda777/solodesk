package com.warda.solodesk.Models;

import javafx.beans.property.*;

public class Payment {
    private final IntegerProperty id;
    private final StringProperty client;
    private final StringProperty date;
    private final DoubleProperty amount;
    private final StringProperty method;
    private final StringProperty note;

    public Payment(int id, String client, String date, double amount, String method, String note) {
        this.id = new SimpleIntegerProperty(id);
        this.client = new SimpleStringProperty(client);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleDoubleProperty(amount);
        this.method = new SimpleStringProperty(method);
        this.note = new SimpleStringProperty(note);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty clientProperty() {
        return client;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public StringProperty methodProperty() {
        return method;
    }

    public StringProperty noteProperty() {
        return note;
    }
}
