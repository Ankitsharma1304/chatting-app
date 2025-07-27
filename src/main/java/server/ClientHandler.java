package server;

import java.io.*;
import java.net.*;
import common.MessageEncryptor;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String nickname;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your nickname:");
            nickname = in.readLine();
            ChatServer.broadcast(nickname + " joined the chat", this);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String decrypted = MessageEncryptor.decrypt(inputLine);
                System.out.println(nickname + ": " + decrypted);
                ChatServer.broadcast(nickname + ": " + decrypted, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ChatServer.removeClient(this);
            ChatServer.broadcast(nickname + " left the chat", this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        String encrypted = MessageEncryptor.encrypt(message);
        out.println(encrypted);
    }
}
