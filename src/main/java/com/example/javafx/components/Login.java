package com.example.javafx.components;

import com.example.javafx.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Login implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    public TextField getUsername() {
        return username;
    }
    public TextField getPassword() {
        return password;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
