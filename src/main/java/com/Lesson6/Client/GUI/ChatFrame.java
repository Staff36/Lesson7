package com.Lesson6.Client.GUI;

import com.Lesson6.Client.GUI.Listeners.LoginActionListener;
import com.Lesson6.Client.GUI.Listeners.LoginKeyListener;
import com.Lesson6.Client.GUI.Listeners.MainActionListener;
import com.Lesson6.Client.GUI.Listeners.MainKeyListener;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ChatFrame extends JFrame{
    private final Icon icon = new ImageIcon("sendIcon.png");
    private JPanel authPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel registrationPanel =new JPanel();
    private final JPanel loginPanel = new JPanel();
    private final JButton sendButton = new JButton(icon);
    private final JTextField inputField = new JTextField();
    private final JTextPane chatArea = new JTextPane();
    private final Consumer<String> messageConsumer;
    private final JLabel authServerRequest = new JLabel();
    private final JLabel registrationServerRequest = new JLabel();
    private boolean connected = false;
    DefaultListModel<String> model;

    public DefaultListModel<String> getModel() {
        return model;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public ChatFrame(String title, Consumer<String> messageConsumer){
        this.messageConsumer = messageConsumer;
        initChatFrame();
        setTitle(title);
        setBounds(50,50,300,340);
        initLoginPanel();
        setVisible(true);
    }

    public void initLoginPanel(){
        add(loginPanel);
        initRegistrationPanel(loginPanel);
        initAuthPanel(loginPanel);
        loginPanel.setVisible(true);
    }

    public void initAuthPanel(JPanel panel){
        registrationPanel.setVisible(false);
        JButton loginButton = new JButton("Sign in");
        JButton signUpButton= new JButton("Sign up");
        JLabel loginLabel = new JLabel("Login:");
        JLabel passwordLabel = new JLabel("Password");
        JPanel logPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JTextField loginField = new JTextField();
        JTextField passwordField = new JTextField();
        loginField.setColumns(16);
        passwordField.setColumns(16);
        passwordField.setColumns(16);

        panel.add(authPanel);
        authPanel.setLayout(new GridLayout(4,1));
        authPanel.add(authServerRequest);
        authPanel.add(logPanel);
        logPanel.setLayout(new FlowLayout());
        authPanel.add(passPanel);
        passPanel.setLayout(new FlowLayout());
        authPanel.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(1,3));

        buttonPanel.add(signUpButton);
        buttonPanel.add(loginButton);
        logPanel.add(loginLabel);
        logPanel.add(loginField);
        passPanel.add(passwordLabel);
        passPanel.add(passwordField);

        loginField.addKeyListener(new LoginKeyListener(messageConsumer, loginField, passwordField,"-auth"));
        passwordField.addKeyListener(new LoginKeyListener(messageConsumer, loginField, passwordField,"-auth"));
        loginButton.addActionListener(new LoginActionListener(messageConsumer, loginField, passwordField,"-auth"));
        signUpButton.addActionListener(e -> {
            authPanel.setVisible(false);
            registrationPanel.setVisible(true);
        });
        authPanel.setVisible(true);
    }

    public void initRegistrationPanel(JPanel panel){
        registrationPanel.setVisible(true);
        authPanel.setVisible(false);
        JButton loginButton = new JButton("Sign up");
        JButton signUpButton = new JButton("Back");
        JLabel nickNameLabel = new JLabel("Nickname:");
        JLabel loginLabel = new JLabel("Login:");
        JLabel passwordLabel = new JLabel("Password");
        JLabel confirmPasswordLabel = new JLabel("Confirm");
        JPanel logPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel confirmPassPanel = new JPanel();
        JPanel nickNamePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JTextField loginField = new JTextField();
        JTextField nickNameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField confirmPasswordField = new JTextField();
        authPanel=new JPanel();
        loginField.setColumns(16);
        passwordField.setColumns(16);
        confirmPasswordField.setColumns(16);
        nickNameField.setColumns(16);

        panel.add(registrationPanel);
        registrationPanel.setLayout(new GridLayout(7,1));
//row1
        registrationPanel.add(registrationServerRequest);
//row2
        registrationPanel.add(logPanel);
        logPanel.setLayout(new FlowLayout());
//row3
        registrationPanel.add(passPanel);
        passPanel.setLayout(new FlowLayout());
//row4
        registrationPanel.add(confirmPassPanel);
        confirmPassPanel.setLayout(new FlowLayout());
//row5
        registrationPanel.add(nickNamePanel);
        nickNamePanel.setLayout(new FlowLayout());
//row6
        registrationPanel.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(1,3));


        logPanel.add(loginLabel);
        logPanel.add(loginField);
        passPanel.add(passwordLabel);
        passPanel.add(passwordField);
        confirmPassPanel.add(confirmPasswordLabel);
        confirmPassPanel.add(confirmPasswordField);
        nickNamePanel.add(nickNameLabel);
        nickNamePanel.add(nickNameField);
        buttonPanel.add(signUpButton);
        buttonPanel.add(loginButton);

        loginField.addKeyListener(new LoginKeyListener(messageConsumer, loginField, passwordField,
                confirmPasswordField, nickNameField, "-register"));
        passwordField.addKeyListener(new LoginKeyListener(messageConsumer, loginField, passwordField,
                confirmPasswordField,nickNameField, "-register"));
        loginButton.addActionListener(new LoginActionListener(messageConsumer, loginField, passwordField,
                confirmPasswordField,nickNameField, "-register"));

        signUpButton.addActionListener(e -> {
            registrationPanel.setVisible(false);
            authPanel.setVisible(true);
        });


    }

    public void initMainPanel(){
        model = new DefaultListModel<>();
        JPanel listOfUsersPanel= new JPanel();
        listOfUsersPanel.setBackground(Color.getHSBColor(250,40, 235));
        listOfUsersPanel.setLayout(new BorderLayout());
        JLabel listOfUsersLabel = new JLabel("People online: ");
        JList<String> list= new JList(model);
        listOfUsersPanel.add(listOfUsersLabel, BorderLayout.NORTH);
        listOfUsersPanel.add(list, BorderLayout.CENTER);
        list.setBackground(Color.getHSBColor(250,40, 235));
        setBounds(0,0,400,600);
        setConnected(true);
        loginPanel.setVisible(false);
        JPanel mainJPanel = new JPanel();
        add(mainJPanel);
        mainJPanel.setLayout(new BorderLayout());
        mainJPanel.add(topPanel,BorderLayout.CENTER);
        mainJPanel.add(bottomPanel,BorderLayout.SOUTH);
        mainJPanel.add(listOfUsersPanel,BorderLayout.EAST);
        setTopPanel();
        setBottomPanel();
        sendButton.addActionListener(new MainActionListener(messageConsumer, inputField, chatArea));
        inputField.addKeyListener(new MainKeyListener(messageConsumer, inputField, chatArea));
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

    public JLabel getAuthServerRequest() {
        return authServerRequest;
    }
    public JLabel getRegistrationServerRequest() {
        return registrationServerRequest;
    }

}
