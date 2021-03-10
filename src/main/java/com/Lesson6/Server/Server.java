package com.Lesson6.Server;

import com.Lesson6.Server.Auth.AuthentificatonService;
import com.Lesson6.Server.Auth.DataBaseController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private final AuthentificatonService authentificatonService;
    private ServerSocket serverSocket;

    public Set<ClientHandler> getHandlers() {
        return handlers;
    }

    private final Set<ClientHandler> handlers;

    public Server() {
        this.authentificatonService = new AuthentificatonService();
        handlers = new HashSet<>();
        try {
            serverSocket= new ServerSocket(8989);
            init();
        } catch (IOException e) {
            throw new RuntimeException("Something wrong", e);
        }
    }


    private void init() throws IOException {
        System.out.println("Server was started");
        DataBaseController.connectToDB();
        System.out.println("-------------------------\nList of registered users");
        DataBaseController.displayAllUsersNickNames();
        System.out.println("-------------------------");
        while (true){
            System.out.println("Waiting for new connection");
            Socket client= serverSocket.accept();
            System.out.println("Client accepted "+client);
            new ClientHandler(client, this );
        }

    }

    public synchronized void subscribe(ClientHandler handler){
        handlers.add(handler);
    }
    public synchronized void unsubscribe(ClientHandler handler){
        handlers.remove(handler);
    }
    public synchronized void broadcast(String message) throws IOException {
        for (ClientHandler handler: handlers) {
            handler.sendMessage(message);
        }
    }

    public AuthentificatonService getAuthentificatonService() {
        return authentificatonService;
    }
    public synchronized boolean isFreeNickName(String nickName){
        for (ClientHandler handler:handlers) {
            if (handler.getNickName().equals(nickName)){
            return false;
            }
        }
        return true;
    }

    public synchronized void singlecast(String nickName, String message) throws IOException{
        for (ClientHandler handler: handlers) {
            if (handler.getNickName().equals(nickName)){
            handler.sendMessage(message);}
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
