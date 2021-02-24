package com.Lesson6.Client.GUI;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener extends Sender implements KeyListener {

    public MyKeyListener(JTextField inputField, JTextPane chatArea) {
        super(inputField, chatArea);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==10)
        send(inputField, chatArea);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
