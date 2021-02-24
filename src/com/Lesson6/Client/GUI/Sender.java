package com.Lesson6.Client.GUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public  abstract class Sender {
    public Sender(JTextField inputField, JTextPane chatArea) {
        this.inputField = inputField;
        this.chatArea = chatArea;
    }

    JTextField inputField;
    JTextPane chatArea;
    public void send(JTextField inputField, JTextPane chatArea){
        if (inputField.getText().isEmpty())
            return;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date();
        String sender= "[YOU "+dateFormat.format(date)+"]: ";
        String msg=inputField.getText()+"\n";
        appendToPane(chatArea,sender, Color.RED);
        appendToPane(chatArea,msg,Color.BLACK);
        inputField.setText("");
    }
    public void appendToPane(JTextPane chatPane, String msg, Color c)
    {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        int len = chatPane.getDocument().getLength();
        chatPane.setCaretPosition(len);
        chatPane.setCharacterAttributes(attributeSet, false);
        chatPane.replaceSelection(msg);
    }
}
