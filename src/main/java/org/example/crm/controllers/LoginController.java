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
import org.example.crm.models.Supervisor;
import org.example.crm.util.CurrentUser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;
import org.example.crm.dao.impl.SupervisorDaoImpl;
import org.example.crm.models.Supervisor;
import org.example.crm.util.CurrentUser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;
import org.example.crm.dao.impl.SupervisorDaoImpl;
import org.example.crm.models.Supervisor;
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
            MainController.navigateTo("view/Supervisor/adminLandingPage-view.fxml" , "Dashboard" , btn);
        } else {
            messageLabel.setText("Invalid Username or Password");
        }
    }
}