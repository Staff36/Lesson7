package com.Lesson6.Server;

import com.Lesson6.Server.Auth.AuthTimer;
import com.Lesson6.Server.Auth.AuthentificationData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;


public class ClientHandler {

    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private final Server server;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String nickName;

    public ClientHandler(Socket socket,Server server) {
        this.socket = socket;
        try {
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            LOGGER.debug("InputStream has already Created");
            this.out = new DataOutputStream(socket.getOutputStream());
            LOGGER.debug("OutputStream has already Created");
            server.getAccountsThreads().submit(this::listen);
        } catch (IOException e) {
            LOGGER.error("Something wrong", e);
            throw new RuntimeException("Something wrong", e);
        }
    }

    private void listen(){
        try {
            doAuth();
        } catch (IOException e) {
            LOGGER.info("Guest was disconnected from server");
        }
        readMessage();
        LOGGER.info("Client" + nickName + " was disconnected from server. Socket "+ socket);

    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public void readMessage()  {
        while (true){
            String message;
            try {
                message = in.readUTF();
                boolean quitter = sender(message);
                if (!quitter){
                    return;}
            } catch (IOException e) {
                try {
                    server.unsubscribe(this);
                    server.broadcast("Server: " + nickName + " has left from chat");
                } catch (IOException ioException) {
                    LOGGER.error("SWW in broadcasting after disconnecting one of users", ioException);
                    throw new RuntimeException("SWW in broadcasting after disconnecting one of users", ioException);
                }
                return;
            }
        }
    }

    private boolean sender(String message) throws IOException {
        if (message.startsWith("/w ")){
            senderForPrivateMessages(message);
        }else if(message.startsWith("/help")) {
            sendMessage(getHelpText());
        }else if(message.startsWith("/q")) {
            sendMessage("Server: Bye, bye");
            server.unsubscribe(this);
            server.broadcast("Server: " + nickName + " has left from chat");
            return false;
        } else if(message.startsWith("/nick")){
            changeNickname(message);
        }else {
            server.broadcast(nickName + ": " + message);
            server.getTextFileController().write(nickName + ": "+message);
        }
        return true;
    }

    private void changeNickname(String message) throws IOException {
        String[] splitMessage = message.split("\\s");
        if (splitMessage.length != 2){
            sendMessage("Server: Incorrect request for change your nickname");
        } else if (!server.isUnregisteredNickName(splitMessage[1])){
            sendMessage("Server: User with this nickname already registered");
        } else{
            server.getDataBaseController().changeNickname(nickName, splitMessage[1]);
            server.broadcast("Server: " + nickName+" changed his nickname, now he is " + splitMessage[1]);
            nickName = splitMessage[1];
            sendMessage("Server: your Nickname is "+nickName);
        }

    }


    private  void senderForPrivateMessages(String message)throws IOException{
        String[] messageArray = message.split("\\s");
        if (messageArray.length < 3){
            sendMessage("Server: incorrect request for sending private message");
            return;
        }
        if (server.isFreeNickName(messageArray[1])) {
            sendMessage("Server: Nickname " + messageArray[1] + " is not found");
        } else if (messageArray[1].equals(nickName)) {
            sendMessage("Server: You try send the private message for yourself, just train your memory...");
        }else{
            message= "Private msg from " + nickName+" for " + messageArray[1] +
                    ": " + buildMessage(messageArray);
            server.singlecast(messageArray[1], message);
            sendMessage(message);
            server.getTextFileController().write(message);
        }
    }

    private String buildMessage(String[] messageArray) {
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = 2; i < messageArray.length; i++) {
            stringBuilder.append(messageArray[i]).append(" ");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        return stringBuilder.toString();
    }

    private void doAuth() throws IOException {
        AuthTimer authTimer = new AuthTimer(12000);
        while (authTimer.checkTheTimeForAuth()) {
            String  input = in.readUTF();
            if (input.startsWith("-auth ")){
                if (doAuthorize(input)){
                    return;
                }
            } else if (input.startsWith("-register ")){
                if (doRegister(input)){
                    return;
                }
            }else{
                LOGGER.debug("User sent invalid authentication request");
                sendMessage("Server: Invalid authentication request");
            }
        }
        LOGGER.debug("Time of Authorization for this User is over");
        sendMessage("Server: Time to login is over, try again later");
    }

    public boolean doRegister(String input) throws IOException {
        String[] registerCredentials = input.split("\\s");
        if (registerCredentials.length == 4){
            if (server.getAuthentificatonService().findUserByCredentials(registerCredentials[1],
                    registerCredentials[2])==null){
                if (server.getAuthentificatonService().nicknameIsFree(registerCredentials[3])){
                    server.getDataBaseController().addUserToDB(registerCredentials[1],
                            registerCredentials[2], registerCredentials[3]);
                    sendMessage("Server: Registration is complete");
                    nickName = registerCredentials[3];
                    sendMessage("Server: your Nickname is "+nickName);
                    server.broadcast("Server: "+ nickName +" joined this chat");
                    server.subscribe(this);
                    sendOnlineUsers();
                    sendLastMessages(10);
                    LOGGER.info("Registered new user " + socket + " with Login: " + registerCredentials[1]);
                    return true;
                } else {
                    LOGGER.debug("User " + socket + " wrote nickname which has already registered");
                    sendMessage("Server: This nickname has already registered");
                }
            }else {
                LOGGER.debug("User " + socket + " wrote login or password which has already registered");
                sendMessage("Server: User with this login or password has already registered");
            }
        } else {
            LOGGER.debug("Invalid authentication request from user " + socket);
            sendMessage("Server: Invalid authentication request");
        }
        return false;
    }

    public boolean doAuthorize(String input) throws IOException {
        String[] credentials = input.split("\\s");
        if (credentials.length == 3) {
            AuthentificationData maybeAuth = server.getAuthentificatonService().
                    findUserByCredentials(credentials[1], credentials[2]);
            if (maybeAuth != null) {
                if (server.isFreeNickName(maybeAuth.getNickName())) {
                    sendMessage("Server: Authentication is complete");
                    nickName = maybeAuth.getNickName();
                    sendMessage("Server: your Nickname is "+nickName);
                    server.broadcast("Server: " + nickName + " joined this chat");
                    server.subscribe(this);
                    LOGGER.info("Authorized user " + socket + " with Login: " + credentials[1]);
                    sendOnlineUsers();
                    sendLastMessages(10);
                    return true;
                } else{
                    LOGGER.debug("User " + socket + " wrote login or password which has already logged ON");
                    sendMessage("Server: This user has already logged in");
                }
            } else {
                LOGGER.debug("User " + socket + " wrote  incorrect or non-existent login or password");
                sendMessage("Server: Unknown user, incorrect login or password");
            }
        } else{
            LOGGER.debug("Invalid authorization request from user " + socket);
            sendMessage("Server: Invalid authorization request");
        }
        return false;
    }

    private void sendOnlineUsers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder("ServerSendOnlineUsers: ");
        Set<ClientHandler> handlers = server.getHandlers();
        for (ClientHandler handler : handlers) {
            stringBuilder.append(handler.getNickName()+" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        sendMessage(stringBuilder.toString());
    }

    public String getNickName() {
        return nickName;
    }

    private String getHelpText(){
        return """
                /W %NICKNAME%     your message write private message for user with name %NICKNAME%
                /help             get Help
                /q                disconnect from server
                /nick %NICKNAME%  change your nickname""";


    }

    public void sendLastMessages(int countOfMessages) throws IOException {
        String text = server.getTextFileController().getAllMessages();
        String[] rows;
        rows = text.split("\n");
        int count = Math.min(countOfMessages, rows.length);
        ArrayList<String> lastMessages = new ArrayList<>();
        for (int i = rows.length-1; i >= rows.length-count; i--) {
            if (rows[i].startsWith("Private msg from ")){
                String[] splitMessage = rows[i].split("\\s");
                if (splitMessage[3].equals(nickName) || splitMessage[5].equals(nickName+":")){
                    lastMessages.add(rows[i]);
                }
            } else {
                lastMessages.add(rows[i]);
            }
        }
        Collections.reverse(lastMessages);
        for (String lastMessage : lastMessages) {
            sendMessage(lastMessage);
        }
    }
}
