package com.Lesson6.Client;

import com.Lesson6.Client.ChatNetworking.ChatNetworkingInterface;
import com.Lesson6.Client.ChatNetworking.ClientNetworking;
import com.Lesson6.Client.GUI.ClientChatFrame;

import java.util.function.Consumer;

public class Client {
    private final ChatNetworkingInterface network;
    private final ClientChatFrame frame;

    public Client(String host, int port) {
        this.network = new ClientNetworking(host, port);
        this.frame = new ClientChatFrame(new Consumer<String>() {
            @Override
            public void accept(String message) {
                network.send(message);
            }
        });
        receive();
    }

    private void receive(){
        new Thread(()->{
            while (true){
                frame.append(network.receive());
            }
        })
                .start();
    }

    public static void main(String[] args) {
        new Client("localhost",8989);
    }
}
