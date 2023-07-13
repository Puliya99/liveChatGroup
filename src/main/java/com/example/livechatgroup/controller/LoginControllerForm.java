package com.example.livechatgroup.controller;

import com.example.livechatgroup.client.Client;
import com.example.livechatgroup.model.UserModel;
import com.example.livechatgroup.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginControllerForm implements Initializable {

    public AnchorPane login;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnJoin;

    @FXML
    void btnJoinOnAction(ActionEvent event) throws IOException {
        try {
            String userName=txtUserName.getText();
            if(UserModel.userVerified(userName)) {
                if (Pattern.matches("[a-zA-Z\\s]+", txtUserName.getText())) {
                    Client client = new Client(txtUserName.getText());
                    Thread thread = new Thread(client);
                    thread.start();
                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Invalid Input").show();
                }
            } else if (UserModel.save(userName)) {
                if (Pattern.matches("[a-zA-Z\\s]+", txtUserName.getText())) {
                    Client client = new Client(txtUserName.getText());
                    Thread thread = new Thread(client);
                    thread.start();
                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Invalid Input").show();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        btnJoin.fire();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Server server = Server.getServerSocket();
            Thread thread = new Thread(server);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
