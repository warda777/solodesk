package com.warda.solodesk.Views;

import com.warda.solodesk.Models.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewFactory {

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/warda/solodesk/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/warda/solodesk/Fxml/Main/Main.fxml"));
        createStage(loader);
    }

    public void setMainCenter(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            Model.getInstance().getMainParent().setCenter(view);
        } catch (IOException e) {
            System.out.println(" Fehler beim Setzen des Inhaltsbereichs");
            e.printStackTrace();
        }
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            System.out.println(" Fehler beim Laden von FXML:");
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/warda/solodesk/Images/user_w.png"))));
        stage.setResizable(false);
        stage.setTitle("SoloDesk");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public void showProfileView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/warda/solodesk/Fxml/Main/Profile.fxml"));
            Parent root = loader.load();

            Model.getInstance().getMainParent().setCenter(root);

        } catch (IOException e) {
            System.out.println(" Fehler beim Laden von Profile.fxml");
            e.printStackTrace();
        }
    }
}

