package com.Lesson6.Client.GUI.Listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class LoginActionListener implements ActionListener {
    Consumer<String> consumer;
    JTextField loginField;
    JTextField passwordField;

    public LoginActionListener(Consumer<String> consumer, JTextField loginField, JTextField passwordField) {
        this.consumer = consumer;
        this.loginField = loginField;
        this.passwordField = passwordField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        consumer.accept("-auth "+loginField.getText()+" "+passwordField.getText());
    }
}
