package com.Lesson6.Client.GUI;

import java.util.function.Consumer;

public class ClientChatFrame implements ChatFrameInteraction{
    private final ChatFrame chatFrame;

    public ClientChatFrame(Consumer<String> messageConsumer) {
        this.chatFrame = new ChatFrame("Elegramm",messageConsumer);
    }

    @Override
    public synchronized void append(String message) {
        if ((message.equals("Server: Authentication is complete") || message.equals("Server: Registration is complete")) &&
                !chatFrame.isConnected()) {
            chatFrame.initMainPanel();
        } else if (!chatFrame.isConnected()){
            chatFrame.getAuthServerRequest().setText(message);
            chatFrame.getRegistrationServerRequest().setText(message);
        }else{
            chatFrame.getChatArea().setText(chatFrame.getChatArea().getText()+message+"\n");
        }
    }

}
