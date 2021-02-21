package com.Lesson6.Server;

import com.Lesson6.Server.Auth.AuthentificationData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final Server server;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String nickName;

    public ClientHandler(Socket socket,Server server) {

        this.socket = socket;
        try {
            this.server= server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(this::listen)
                    .start();

        } catch (IOException e) {
            throw new RuntimeException("Something wrong, e");
        }
    }
    private void listen(){
        try {
            doAuth();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readMessage();
    }
    public void sendMessage(String message) throws IOException {
            out.writeUTF(message);
    }
    public void readMessage()  {
        while (true){
            String message= null;
            try {
                message = in.readUTF();
                sender(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void sender(String message) throws IOException {
        if (message.startsWith("/w ")){
            String[] messageArray=message.split(" ");
            if (!server.isFreeNickName(messageArray[1])) {
                message= buildMessage(messageArray);
                server.singlecast(messageArray[1], nickName+" PERSONAL: "+message);
                sendMessage("PERSONAL FOR "+messageArray[1]+": "+message);
            } else{
                sendMessage("User with nick "+messageArray[1]+" is not found");
            }
        }else {
            server.broadcast(nickName+": "+message);
        }
    }

    private String buildMessage(String[] messageArray) {
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = 2; i < messageArray.length; i++) {
            stringBuilder.append(messageArray[i] + " ");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        return stringBuilder.toString();
    }

    private void doAuth() throws IOException {
        while (true) {
            String  input= in.readUTF();
            if (input.startsWith("-auth ")){
                String[] credentials = input.split("\\s");
                if (credentials.length==3) {
                    AuthentificationData maybeAuth = server.getAuthentificatonService().
                            findUserByCredentials(credentials[1], credentials[2]);
                    if (maybeAuth != null) {
                        if (server.isFreeNickName(maybeAuth.getNickName())) {
                            sendMessage("Server: Authentification is complete");
                            nickName = maybeAuth.getNickName();
                            server.broadcast("logged in");
                            server.subscribe(this);
                            return;
                        } else{
                            sendMessage("This user is already logged in");
                        }
                    } else {
                        sendMessage("Unknown user, incorrect login or password");
                    }
                } else{
                    sendMessage("Invalid authentification request");
                }
            } else{
                sendMessage("Invalid authentification request");
            }
        }
    }

    public String getNickName() {
        return nickName;
    }
}
