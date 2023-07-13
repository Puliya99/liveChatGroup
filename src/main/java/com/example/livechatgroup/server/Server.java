package com.example.livechatgroup.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static Server server;
    private final ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(4008);
        System.out.println("Server Started");
    }
    public static Server getServerSocket() throws IOException {
        return server == null ? server = new Server() : server;
    }
    public void run(){
        while (!serverSocket.isClosed()){
            System.out.println("Listening...");
            try {
                Socket accepted = serverSocket.accept();
                ClientHandler clientHandler =new ClientHandler(accepted);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                //throw new RuntimeException(e);
                closeServer();
            }
        }
    }
    private void closeServer() {
        try {
            if (serverSocket != null) {
                System.out.println("Server Close");
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
