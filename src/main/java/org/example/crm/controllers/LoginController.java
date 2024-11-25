package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;
import org.example.crm.dao.impl.SupervisorDaoImpl;
import org.example.crm.util.CurrentUser;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button btn;

    @FXML
    private void handleLogin() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        SupervisorDaoImpl supervisor = new SupervisorDaoImpl();


        if (supervisor.verifyLogin(enteredUsername,enteredPassword)) {
            CurrentUser.setLoggedInAdmin(enteredUsername);
            try {

                // Load the AddAgent screen after successful login
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/Supervisor/SupervisorPage-view.fxml"));
                Stage window = (Stage) messageLabel.getScene().getWindow(); // Use messageLabel instead of btn

                // Set the new scene on the same stage (window)
                window.setScene(new Scene(loader.load(), 600, 400));

            } catch (IOException e) {
                e.printStackTrace();  // Print the error details for debugging
                messageLabel.setText("Error loading the Add Agent screen.");
            }
        } else {
            messageLabel.setText("Invalid Username or Password");
        }
    }
}
