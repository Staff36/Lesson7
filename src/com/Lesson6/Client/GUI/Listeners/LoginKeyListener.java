package com.Lesson6.Client.GUI.Listeners;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class LoginKeyListener implements KeyListener {
    Consumer<String> consumer;
    JTextField loginField;
    JTextField passwordField;

    public LoginKeyListener(Consumer<String> consumer, JTextField loginField, JTextField passwordField) {
        this.consumer = consumer;
        this.loginField = loginField;
        this.passwordField = passwordField;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==10){
            consumer.accept("-auth "+loginField.getText()+" "+passwordField.getText());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
