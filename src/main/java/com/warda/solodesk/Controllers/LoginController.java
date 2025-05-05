package com.warda.solodesk.Controllers;

import com.warda.solodesk.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public PasswordField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public TextField user_fld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event -> onLogin());
    }

    protected void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();

        String username = user_fld.getText();
        String password = password_fld.getText();

        if (Model.getInstance().checkLogin(username, password)) {
            Model.getInstance().getViewFactory().showMainWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        } else {
            user_fld.setText("");
            password_fld.setText("");
            error_lbl.setText("Hoppla! Benutzername oder Passwort stimmt nicht.");
        }
    }
    protected Stage getCurrentStage() {
        return (Stage) error_lbl.getScene().getWindow();
    }

}

