package com.Lesson6.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ClientChatFrame implements ChatFrameInteraction{
private final ChatFrame chatFrame;
private final Consumer<String> messageConsumer;

    public ClientChatFrame(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
        this.chatFrame = new ChatFrame("Elegramm");
    }

    @Override
    public void append(String message) {

    }

    @Override
    public String submit() {
        return null;
    }
}
