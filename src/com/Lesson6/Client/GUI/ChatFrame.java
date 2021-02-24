package com.Lesson6.Client.GUI;

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame{
    JPanel topPanel= new JPanel();
    JPanel bottomPanel= new JPanel();
    Icon icon= new ImageIcon("sendIcon.png");
    JButton sendButton= new JButton(icon);
    JTextField inputField= new JTextField();
    JTextPane chatArea= new JTextPane();

    public ChatFrame(String title){
        setBounds(0,0,400,600);
        setTitle(title);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(topPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
        setTopPanel();
        setBottomPanel();
        sendButton.addActionListener(new MyActionListener(inputField,chatArea));
        inputField.addKeyListener(new MyKeyListener(inputField,chatArea));
        setVisible(true);
    }

    private void setTopPanel(){
        topPanel.setLayout(new BorderLayout());
        topPanel.add(chatArea,BorderLayout.CENTER);
    }
    private void setBottomPanel(){
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(sendButton,BorderLayout.EAST);
        bottomPanel.add(inputField,BorderLayout.CENTER);
    }
}
