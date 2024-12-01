package org.example.crm.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class NavigationHelper {
    public static void navigateTo(String fxmlPath, String title, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource(fxmlPath));
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(loader.load()));
            window.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
