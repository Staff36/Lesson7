package com.Lesson6.Client.GUI;

import javax.swing.*;
import java.util.function.Consumer;

public  abstract class Sender {
    protected Consumer<String> consumer;
    protected JTextField inputField;
    protected JTextPane chatArea;
    DefaultListModel<String> model;
    public Sender(Consumer<String> consumer, JTextField inputField, JTextPane chatArea) {
        this.consumer = consumer;
        this.inputField = inputField;
        this.chatArea = chatArea;
    }

    public void send(JTextField inputField){
        if (inputField.getText().isBlank())
            return;
        consumer.accept(inputField.getText());
        inputField.setText("");
    }
}
