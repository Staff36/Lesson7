package com.Lesson6.Server;

import com.Lesson6.Server.Auth.AuthentificatonService;
import com.Lesson6.Server.Auth.Controllers.DataBaseController;
import com.Lesson6.Server.Auth.Controllers.TextFileController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class.getName());
    private final AuthentificatonService authentificatonService;
    private final ServerSocket serverSocket;
    private final Set<ClientHandler> handlers;
    private final DataBaseController dataBaseController;
    private final TextFileController textFileController;
    private final ExecutorService accountsThreads;

    public Set<ClientHandler> getHandlers() {
        return handlers;
    }

    public DataBaseController getDataBaseController() {
        return dataBaseController;
    }

    public Server() {
        this.textFileController = new TextFileController("chatHistory.txt");
        LOGGER.debug("Text file controller has successful created");
        this.authentificatonService = new AuthentificatonService();
        LOGGER.debug("Service of authentication has successful created");
        handlers = new HashSet<>();
        dataBaseController = new DataBaseController();
        LOGGER.debug("Data base controller has successful created");
        accountsThreads = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(8989);
            init();
        } catch (IOException e) {
            LOGGER.error("Something wrong error to starting Server", e);
            throw new RuntimeException("Something wrong", e);
        }
    }

    private void init() throws IOException {
        LOGGER.info("Server was started");
        dataBaseController.connectToDB();
        LOGGER.debug("------------------------- List of registered users");
        dataBaseController.displayAllUsersNickNames();
        LOGGER.debug("-------------------------");
        while (true){
            LOGGER.info("Server waiting for new connection");
            Socket client = serverSocket.accept();
            LOGGER.info("Client accepted " + client);
            System.out.println("Client accepted " + client);
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
        for (ClientHandler handler : handlers) {
            handler.sendMessage(message);
        }
    }

    public AuthentificatonService getAuthentificatonService() {
        return authentificatonService;
    }

    public synchronized boolean isFreeNickName(String nickName){
        for (ClientHandler handler : handlers) {
            if (handler.getNickName().equals(nickName)){
            return false;
            }
        }
        return true;
    }

    public synchronized void singlecast(String nickName, String message) throws IOException{
        for (ClientHandler handler : handlers) {
            if (handler.getNickName().equals(nickName)){
                handler.sendMessage(message);
            }
        }
    }

    public TextFileController getTextFileController() {
        return textFileController;
    }

    public synchronized boolean isUnregisteredNickName(String nickName){
        List<String> listOfNicknames = dataBaseController.getAllUsersNickNames();
        for (String name : listOfNicknames) {
            if (name.equals(nickName)){
                return false;
            }
        }
        return true;
    }

    public ExecutorService getAccountsThreads() {
        return accountsThreads;
    }

    public static void main(String[] args) {
        new Server();
    }
}
