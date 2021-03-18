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
        } else if (!chatFrame.isConnected()) {
            chatFrame.getAuthServerRequest().setText(message);
            chatFrame.getRegistrationServerRequest().setText(message);
        } else if (message.startsWith("ServerSendOnlineUsers: ")) {
            addUsers(message);
        } else if (message.startsWith("Server: ") && message.contains(" joined this chat")) {
            addNewUser(message);
        } else if (message.startsWith("Server: ") && message.contains(" has left from chat")) {
            removeNewUser(message);
        } else if(message.startsWith("Server: ") && message.contains(" changed his nickname, now he is ")){
            changeUsersNickname(message);
        } else{
            chatFrame.getChatArea().setText(chatFrame.getChatArea().getText()+message+"\n");
        }
    }

    private void addUsers(String message){
        String[] array = message.split("\\s");
        for (int i = 1; i < array.length; i++) {
            chatFrame.getModel().addElement(array[i]);
        }
    }

    private void changeUsersNickname(String message){
        String[] array = message.split("\\s");
        chatFrame.getModel().removeElement(array[1]);
        chatFrame.getModel().addElement(array[array.length-1]);
    }

    private String parseNickname(String message){
        String[] array = message.split("\\s");
        return array[1];
    }

    private void addNewUser(String message){
        chatFrame.getModel().addElement(parseNickname(message));
    }
    private void removeNewUser(String message){
        chatFrame.getModel().removeElement(parseNickname(message));
    }

}
