package com.example.livechatgroup.client;

import com.example.livechatgroup.controller.ClientFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Client implements Runnable, Serializable {
    private final String name;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private ClientFormController clientFormController;

    public Client(String name) throws IOException {
        this.name =name;
        socket = new Socket("localhost",4008);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.writeUTF(name);
        outputStream.flush();
        try {
            loadScene();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private void receiveImage() throws IOException {
        String utf = inputStream.readUTF();
        int size =inputStream.readInt();
        byte[] bytes = new byte[size];
        inputStream.readFully(bytes);
        clientFormController.setImage(bytes,utf);
    }
    @Override
    public void run() {
        try{
            outputStream.writeUTF(name+" Joined to Chat");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true){
            try {
                String message = inputStream.readUTF();
                if (message.equals("*image*")){
                    receiveImage();
                }else {
                    clientFormController.writeMessage(message);
                }
            }catch (IOException e){
                try {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendMessage(String msg) throws IOException {
        outputStream.writeUTF(msg);
        outputStream.flush();
    }

    private void loadScene() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_form.fxml"));
        Parent parent =loader.load();
        clientFormController = loader.getController();
        clientFormController.setClient(this);
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.setTitle(name + " Chat");
        stage.show();
        stage.setOnCloseRequest(event->{
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        stage.setOnCloseRequest(event ->{
            try {
                outputStream.writeUTF("Has left! \uD83D\uDE13");
                outputStream.flush();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public String getName(){
        return name;
    }

    public void sendImage(byte[] bytes) throws IOException {
        outputStream.writeUTF("*image*");
        outputStream.writeInt(bytes.length);
        outputStream.write(bytes);
        outputStream.flush();
    }
}
