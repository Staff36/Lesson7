package com.Lesson6.Client.GUI.Listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class LoginActionListener implements ActionListener {
    private  JTextField nickname;
    private Consumer<String> consumer;
    private JTextField loginField;
    private JTextField passwordField;
    private JTextField confirmPassword;
    private String prefix;


    public LoginActionListener(Consumer<String> consumer, JTextField loginField, JTextField passwordField, String prefix) {
        this.consumer = consumer;
        this.loginField = loginField;
        this.passwordField = passwordField;
        this.prefix=prefix;
    }
    public LoginActionListener(Consumer<String> consumer, JTextField loginField, JTextField passwordField, JTextField confirmPassword, JTextField nickname, String prefix) {
        this.consumer = consumer;
        this.loginField = loginField;
        this.passwordField = passwordField;
        this.confirmPassword=confirmPassword;
        this.prefix=prefix;
        this.nickname= nickname;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
    private static String removeSpaces(JTextField textField){
        return textField.getText().replaceAll("\\s","");
    }
}
