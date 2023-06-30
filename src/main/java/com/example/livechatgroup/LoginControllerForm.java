package com.example.livechatgroup;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginControllerForm extends Application {

    public AnchorPane login;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"))));
        stage.show();
    }
    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnJoin;

    @FXML
    void btnJoinOnAction(ActionEvent event) throws IOException {
        if (txtUserName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Input User Name").show();
        } else {
            Stage window = (Stage) login.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/client_Server_form.fxml"))));
        }
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        btnJoin.fire();
    }
}
