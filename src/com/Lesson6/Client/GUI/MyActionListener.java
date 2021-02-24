package com.Lesson6.Client.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener extends Sender implements ActionListener {
    public MyActionListener(JTextField inputField, JTextPane chatArea) {
        super(inputField, chatArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        send(inputField, chatArea);
    }
}
