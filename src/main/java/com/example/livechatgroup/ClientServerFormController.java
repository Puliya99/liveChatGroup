package com.example.livechatgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientServerFormController {

    public Button btnAdd;
    public Button btnLeave;
    public AnchorPane ChatApplication;
    public TextArea txtAreaChat;
    public TextField txtChat;
    public ImageView imgEmoji;
    public ImageView imgSend;
    public ImageView imgGallery;
    public Label lblNames;
    public AnchorPane Emoji;
    public ImageView imgMenu;
    public VBox vboxMenuBar;


    public void txtChatOnAction(javafx.event.ActionEvent actionEvent) {
    }

    public void imgEmojiOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        Emoji.setVisible(true);
    }

    public void imgSendOnAction(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void imgGallaryOnAction(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void imgMenuOnAction(MouseEvent mouseEvent) {
        vboxMenuBar.setVisible(true);
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage)  ChatApplication.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
    }

    public void btnLeaveOnAction(ActionEvent actionEvent) throws IOException {
    }


    public void vboxMenuBarExitedOnAction(MouseEvent mouseEvent) {
        vboxMenuBar.setVisible(false);
    }

    public void EmojiExitedOnAction(MouseEvent mouseEvent) {
        Emoji.setVisible(false);
    }
}
