package com.Lesson6.Client.GUI;

import javax.swing.*;
import java.util.function.Consumer;

public  abstract class Sender {
    protected Consumer<String> consumer;
    protected JTextField inputField;
    public Sender(Consumer<String> consumer, JTextField inputField, JTextPane chatArea) {
        this.consumer = consumer;
        this.inputField = inputField;
    }

    public void send(JTextField inputField){
        if (inputField.getText().isBlank())
            return;
        consumer.accept(inputField.getText());
        inputField.setText("");
    }
}
