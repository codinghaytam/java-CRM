package org.example.crm.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;

import java.io.IOException;
import java.util.Set;

public class MainController {

    @FXML
    private Button adminLoginButton;

    @FXML
    private Button agentLoginButton;

    @FXML
    private void handleAdminLogin(ActionEvent event) {
        navigateTo("view/Supervisor/Login-view.fxml", "Admin Login",false,adminLoginButton);
    }

    @FXML
    private void handleAgentLogin(ActionEvent event) {
        navigateTo("view/Supervisor/AgentLogin-view.fxml", "Agent Login",false,agentLoginButton);
    }

    Set<String> openedWindows;

    static void navigateTo(String fxmlFilePath, String title, boolean openInNewWindow,Button btn) {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFilePath));
            Parent root = loader.load();

            if (openInNewWindow) {
                // Create a new stage for the new window
                Stage newStage = new Stage();
                newStage.setTitle(title);

                // Set the scene for the new window
                Scene newScene = new Scene(root);
                newStage.setScene(newScene);

                // Show the new window
                newStage.show();
            } else {
                // Get the current stage (window) from an existing UI element
                Stage currentStage = (Stage) btn.getScene().getWindow();

                // If the stage is not found (i.e., root is not yet in a scene), try to get the current stage from somewhere else
                if (currentStage == null) {
                    // Example of getting the stage from another UI element (like a button)
                    currentStage = (Stage) btn.getScene().getWindow();
                }

                // Set the new scene and title
                Scene newScene = new Scene(root);
                currentStage.setScene(newScene);
                currentStage.setTitle(title);  // Optionally set a new title
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
