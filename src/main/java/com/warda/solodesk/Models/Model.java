package com.warda.solodesk.Models;

import com.warda.solodesk.Views.ViewFactory;
import javafx.scene.layout.BorderPane;

public class Model {
    private static Model instance;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private BorderPane mainParent;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
    }

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public boolean checkLogin(String username, String password) {
        return databaseDriver.validateLogin(username, password);
    }

    public void setMainParent(BorderPane pane) {
        this.mainParent = pane;
    }

    public BorderPane getMainParent() {
        return mainParent;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }
}
