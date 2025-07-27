package client;

import common.MessageEncryptor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatGUI extends Application {
    private PrintWriter out;
    private TextArea chatArea;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Enter nickname");

        chatArea = new TextArea();
        chatArea.setEditable(false);

        TextField messageField = new TextField();
        messageField.setPromptText("Type your message");

        Button sendButton = new Button("Send");

        VBox vbox = new VBox(10, nicknameField, chatArea, new HBox(10, messageField, sendButton));
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Chat Client");
        primaryStage.show();

        sendButton.setOnAction(e -> {
            String msg = messageField.getText();
            if (out != null && !msg.isEmpty()) {
                out.println(MessageEncryptor.encrypt(msg));
                messageField.clear();
            }
        });

        nicknameField.setOnAction(e -> {
            try {
                Socket socket = new Socket("localhost", 12345);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                new Thread(() -> {
                    try {
                        String line;
                        while ((line = in.readLine()) != null) {
                            String decrypted = MessageEncryptor.decrypt(line);
                            chatArea.appendText(decrypted + "\n");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }).start();

                out.println(nicknameField.getText());
                nicknameField.setDisable(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
