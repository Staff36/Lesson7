package com.Lesson6.Client.GUI.Listeners;

import com.Lesson6.Client.GUI.Sender;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class MainKeyListener extends Sender implements KeyListener {

    public MainKeyListener(Consumer<String> consumer, JTextField inputField, JTextPane chatArea) {
        super(consumer, inputField, chatArea);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10)
        super.send(inputField);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
