package com.Lesson6.Client.GUI.Listeners;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class LoginKeyListener implements KeyListener {
    private  JTextField nickname;
    private Consumer<String> consumer;
    private JTextField loginField;
    private JTextField passwordField;
    private JTextField confirmPassword;
    private String prefix;

    public LoginKeyListener(Consumer<String> consumer, JTextField loginField, JTextField passwordField, String prefix) {
        this.consumer = consumer;
        this.loginField = loginField;
        this.passwordField = passwordField;
        this.prefix=prefix;
    }
    public LoginKeyListener(Consumer<String> consumer, JTextField loginField, JTextField passwordField, JTextField confirmPassword, JTextField nickname, String prefix) {
        this.consumer = consumer;
        this.loginField = loginField;
        this.passwordField = passwordField;
        this.confirmPassword=confirmPassword;
        this.prefix=prefix;
        this.nickname= nickname;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==10){
            String login= removeSpaces(loginField);
            String password= removeSpaces(passwordField);
            if (login.isBlank()||password.isBlank()){
                return;
            }
            switch (prefix){
                case "-auth":{
                    consumer.accept(prefix+" "+login+" "+password);
                    break;}
                case "-register":
                    String confirm= removeSpaces(confirmPassword);
                    String nick= removeSpaces(nickname);
                    if (confirm.equals(password)&&!nick.isBlank()){
                        consumer.accept(prefix+" "+login+" "+password+" "+nick);
                        break;
                    }
            }
        }
    }
    private static String removeSpaces(JTextField textField){
        return textField.getText().replaceAll("\\s","");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
