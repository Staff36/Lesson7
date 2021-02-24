package com.Lesson6.Client;

import com.Lesson6.Client.ChatNetworking.ChatNetworking;
import com.Lesson6.Client.ChatNetworking.Client;
import com.Lesson6.Client.GUI.ClientChatFrame;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ChatAdapter {
    private final ChatNetworking network;
    private final ClientChatFrame frame;

    public ChatAdapter(String host, int port) {
        this.network = new Client(host, port);
        this.frame = new ClientChatFrame(new Consumer<String>() {
            @Override
            public void accept(String message) {
                network.send(message);
            }
        });
    }

        private void send(String message){

       }

    private void receive(String message){

    }
}
