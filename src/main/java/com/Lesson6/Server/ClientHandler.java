package com.Lesson6.Server;

import com.Lesson6.Server.Auth.AuthTimer;
import com.Lesson6.Server.Auth.AuthentificationData;
import com.Lesson6.Server.Auth.DataBaseController;

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
            System.out.println("Guest was disconnected from server");
        }
        readMessage();
        System.out.println("Client was disconnected from server. Socket "+ socket);
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
                try {
                    server.unsubscribe(this);
                    server.broadcast("Server: "+this.getNickName()+" has left from chat");
                } catch (IOException ioException) {
                    throw new RuntimeException("SWW in broadcasting after disconnecting one of users", ioException);
                }
                return;
            }
        }
    }

    private void sender(String message) throws IOException {

        if (message.startsWith("/w ")){
            String[] messageArray=message.split("\\s");
            if (!server.isFreeNickName(messageArray[1])) {
                message= buildMessage(messageArray);
                server.singlecast(messageArray[1], nickName+" PERSONAL: "+message);
                sendMessage("PERSONAL FOR "+messageArray[1]+": "+message);
            } else{
                sendMessage("Server: Nickname "+messageArray[1]+" is not found");
            }
        }else if(message.startsWith("/help")){
            sendMessage(getHelpText());

        } else {
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
        AuthTimer authTimer= new AuthTimer(12000);
        while (authTimer.checkTheTimeForAuth()) {
            String  input= in.readUTF();
            if (input.startsWith("-auth ")){
                if (doAuthorize(input)){
                    return;
                }
            } else if (input.startsWith("-register ")){
                if (doRegister(input)){
                    return;
                }
            }else{
                sendMessage("Server: Invalid authentification request");
            }
        }
        sendMessage("Server: Time to login is over, try again later");
    }

    public boolean doRegister(String input) throws IOException {
        String[] registerCredentials = input.split("\\s");
        if (registerCredentials.length==4){
            if (server.getAuthentificatonService().findUserByCredentials(registerCredentials[1], registerCredentials[2])==null){
                if (server.getAuthentificatonService().nicknameIsFree(registerCredentials[3])){
                    DataBaseController.addUserToDB(registerCredentials[1],registerCredentials[2],registerCredentials[3]);
                    sendMessage("Server: Registration is complete");
                    server.broadcast("Server:"+this.getNickName()+" joined this chat");
                    server.subscribe(this);
                    System.out.println("Registered new user with nick: "+registerCredentials[3]);
                    return true;
                } else {
                    sendMessage("Server: This nickname has already registered");
                }
            }else {
                sendMessage("Server: User with this password has already registered");
            }
        } else {
            sendMessage("Server: Invalid authentification request");
        }
        return false;
    }
    public boolean doAuthorize(String input) throws IOException {
        String[] credentials = input.split("\\s");
        if (credentials.length==3) {
            AuthentificationData maybeAuth = server.getAuthentificatonService().
                    findUserByCredentials(credentials[1], credentials[2]);
            if (maybeAuth != null) {
                if (server.isFreeNickName(maybeAuth.getNickName())) {
                    sendMessage("Server: Authentification is complete");
                    nickName = maybeAuth.getNickName();
                    server.broadcast("Server:"+this.getNickName()+" joined this chat");
                    server.subscribe(this);
                    return true;
                } else{
                    sendMessage("Server: This user has already logged in");
                }
            } else {
                sendMessage("Server: Unknown user, incorrect login or password");
            }
        } else{
            sendMessage("Server: Invalid authentification request");
        }
        return false;
    }

    public String getNickName() {
        return nickName;
    }

    private String getHelpText(){
        String text ="/W %NICKNAME%     your message write private message for user with name %NICKNAME%\n" +
                     "/help             get Help";
        return text;
    }
}
