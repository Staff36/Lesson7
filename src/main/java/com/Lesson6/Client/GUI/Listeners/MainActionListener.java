package com.Lesson6.Client.GUI.Listeners;

import com.Lesson6.Client.GUI.Sender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class MainActionListener extends Sender implements ActionListener {

    public MainActionListener(Consumer<String> consumer, JTextField inputField, JTextPane chatArea) {
        super(consumer, inputField, chatArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        send(inputField, chatArea);
    }
}
