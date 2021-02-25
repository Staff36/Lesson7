package com.Lesson6.Client.GUI;

import com.Lesson6.Client.GUI.Listeners.LoginKeyListener;
import com.Lesson6.Client.GUI.Listeners.MainActionListener;
import com.Lesson6.Client.GUI.Listeners.MainKeyListener;
import com.Lesson6.Client.GUI.Listeners.LoginActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ChatFrame extends JFrame{
    private final JPanel topPanel= new JPanel();
    private final JPanel bottomPanel= new JPanel();
    private final Icon icon= new ImageIcon("sendIcon.png");
    private final JButton sendButton= new JButton(icon);
    private final JTextField inputField= new JTextField();
    private final JTextPane chatArea= new JTextPane();
    private final Consumer<String> messageConsumer;
    private boolean connected=false;
    private JLabel serverRequest;
    private JPanel authPanel;

    public void setConnected(boolean connected) {
        this.connected = connected;
    }


    public ChatFrame(String title, Consumer<String> messageConsumer){

        this.messageConsumer=messageConsumer;
        initChatFrame();
        setTitle(title);
        initLoginPanel();
        setVisible(true);
    }

    public void initLoginPanel(){
        setBounds(50,50,300,340);
        JButton loginButton= new JButton("Login");
        JTextField loginField = new JTextField();
        JLabel loginLabel = new JLabel("Login:");
        JPanel logPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JTextField passwordField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        serverRequest= new JLabel();
        authPanel=new JPanel();
        loginField.setColumns(16);
        passwordField.setColumns(16);
        JPanel buttonPanel = new JPanel();

        add(authPanel);
        authPanel.setLayout(new GridLayout(4,1));

        authPanel.add(serverRequest);

        authPanel.add(logPanel);
        logPanel.setLayout(new FlowLayout());

        authPanel.add(passPanel);
        passPanel.setLayout(new FlowLayout());

        authPanel.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(1,3));

        buttonPanel.add(new Label());
        buttonPanel.add(loginButton);
        logPanel.add(loginLabel);
        logPanel.add(loginField);
        passPanel.add(passwordLabel);
        passPanel.add(passwordField);
        loginField.addKeyListener(new LoginKeyListener(messageConsumer, loginField, passwordField));
        passwordField.addKeyListener(new LoginKeyListener(messageConsumer, loginField, passwordField));
        loginButton.addActionListener(new LoginActionListener(messageConsumer, loginField, passwordField));
        authPanel.setVisible(true);

    }
    public void initMainPanel(){
        setBounds(0,0,400,600);
        setConnected(true);
        authPanel.setVisible(false);
        JPanel mainJpanel = new JPanel();
        add(mainJpanel);
        mainJpanel.setLayout(new BorderLayout());
        mainJpanel.add(topPanel,BorderLayout.CENTER);
        mainJpanel.add(bottomPanel,BorderLayout.SOUTH);
        setTopPanel();
        setBottomPanel();
        MainActionListener myActionListener = new MainActionListener(messageConsumer, inputField, chatArea);
        MainKeyListener myKeyListener = new MainKeyListener(messageConsumer, inputField, chatArea);
        sendButton.addActionListener(myActionListener);
        inputField.addKeyListener(myKeyListener);
    }

    public void initChatFrame(){
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JTextPane getChatArea() {
        return chatArea;
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

    public boolean isConnected() {
        return connected;
    }

    public JLabel getServerRequest() {
        return serverRequest;
    }

}
