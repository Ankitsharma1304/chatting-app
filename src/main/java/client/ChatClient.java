package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import common.MessageEncryptor;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        Thread readThread = new Thread(() -> {
            try {
                String input;
                while ((input = in.readLine()) != null) {
                    System.out.println("[Encrypted] " + input);
                    System.out.println("[Decrypted] " + common.MessageEncryptor.decrypt(input));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readThread.start();

        while (true) {
            String msg = scanner.nextLine();
            out.println(MessageEncryptor.encrypt(msg));
        }
    }
}
