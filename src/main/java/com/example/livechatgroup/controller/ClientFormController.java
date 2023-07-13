package com.example.livechatgroup.controller;

import com.example.livechatgroup.client.Client;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {

    public AnchorPane ChatApplication;
    public TextField txtChat;
    public ImageView imgEmoji;
    public ImageView imgSend;
    public ImageView imgGallery;
    public AnchorPane EmojiPane;
    public VBox vBox;
    public GridPane emojiGridPane;
    public ScrollPane scrollPane;
    private Client client;
    private final String[] emojis = {
            "\uD83D\uDE00",
            "\uD83D\uDE01",
            "\uD83D\uDE02",
            "\uD83D\uDE03",
            "\uD83D\uDE04",
            "\uD83D\uDE05",
            "\uD83D\uDE06",
            "\uD83D\uDE07",
            "\uD83D\uDE08",
            "\uD83D\uDE09",
            "\uD83D\uDE0A",
            "\uD83D\uDE0B",
            "\uD83D\uDE0C",
            "\uD83D\uDE0D",
            "\uD83D\uDE0E",
            "\uD83D\uDE0F",
            "\uD83D\uDE10",
            "\uD83D\uDE11",
            "\uD83D\uDE12",
            "\uD83D\uDE13"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmojiPane.setVisible(false);
        int buttonIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (buttonIndex < emojis.length) {
                    String emoji = emojis[buttonIndex];
                    JFXButton emojiButoon = createEmojiButton(emoji);
                    emojiGridPane.add(emojiButoon, column, row);
                    buttonIndex++;
                } else {
                    break;
                }
            }
        }
    }

    private JFXButton createEmojiButton(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }

    private void emojiButtonAction(ActionEvent actionEvent) {
        JFXButton button = (JFXButton) actionEvent.getSource();
        txtChat.appendText(button.getText());
    }

    @FXML
    private void imgEmojiOnAction(MouseEvent actionEvent) {
        EmojiPane.setVisible(!EmojiPane.isVisible());
    }

    public void imgSendOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            String text = txtChat.getText();
            if (!text.equals("")) {
                appendText(text);
                client.sendMessage(text);
                txtChat.clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "message is empty").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong : server down").show();
        }
    }

    private void appendText(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 30");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  purple;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vBox.getChildren().add(hBox);
        new Thread(() -> {
        }).start();
    }

    public void imgGallaryOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                HBox hBox = new HBox();
                hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 30; -fx-alignment: center-right;");
                ImageView imageView = new ImageView(new Image(new FileInputStream(selectedFile)));
                imageView.setStyle("-fx-padding: 30px;");
                imageView.setFitHeight(180);
                imageView.setFitWidth(100);
                hBox.getChildren().addAll(imageView);
                vBox.getChildren().add(hBox);
                client.sendImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void EmojiExitedOnAction(MouseEvent mouseEvent) {
        EmojiPane.setVisible(false);
    }

    public void writeMessage(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(() -> {
            vBox.getChildren().add(hBox);
        });
    }

    public void setImage(byte[] bytes, String sender) {
        HBox hBox = new HBox();
        Label messaheLbl = new Label(sender);
        messaheLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center;-fx-content-display: left;-fx-padding: 30;-fx-max-width: 350;");
        hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 30; " + (sender.equals(client.getName()) ? "-fx-alignment: center-right;" : "-fx-alignment: center-left;"));
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setStyle("-fx-padding: 30px;");
            imageView.setFitHeight(180);
            imageView.setFitWidth(100);
            hBox.getChildren().addAll(messaheLbl, imageView);
            vBox.getChildren().add(hBox);
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
