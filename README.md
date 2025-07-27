# Java Chat App

A simple peer-to-peer encrypted chat application using Java, JavaFX, and Sockets.

## Features
- Group messaging
- Nickname-based chat
- Message encryption
- JavaFX GUI (ChatGUI.java)
- Console client (ChatClient.java)

## Requirements
- Java 17+
- Maven
- JavaFX SDK (add to module path)

## How to Run
1. Compile: `mvn clean compile`
2. Run Server:
   ```
   mvn exec:java -Dexec.mainClass="server.ChatServer"
   ```
3. Run Client:
   - CLI client:
     ```
     mvn exec:java -Dexec.mainClass="client.ChatClient"
     ```
   - JavaFX GUI:
     ```
     mvn exec:java -Dexec.mainClass="client.ChatGUI"
     ```

## Folder Structure
```
src/
 └── main/
     ├── java/
     │   ├── client/
     │   │   ├── ChatClient.java
     │   │   └── ChatGUI.java
     │   ├── server/
     │   │   ├── ChatServer.java
     │   │   └── ClientHandler.java
     │   └── common/
     │       └── MessageEncryptor.java
     └── resources/
         └── application.properties (optional)
```
