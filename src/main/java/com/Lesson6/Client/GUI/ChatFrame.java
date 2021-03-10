package com.Lesson6.Client.GUI;

import com.Lesson6.Client.GUI.Listeners.LoginActionListener;
import com.Lesson6.Client.GUI.Listeners.LoginKeyListener;
import com.Lesson6.Client.GUI.Listeners.MainActionListener;
import com.Lesson6.Client.GUI.Listeners.MainKeyListener;

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
    private JLabel authServerRequest = new JLabel();
    private JLabel registrationServerRequest = new JLabel();
    private JPanel authPanel=new JPanel();;
    private JPanel registrationPanel=new JPanel();
    private JPanel loginPanel= new JPanel();
    public void setConnected(boolean connected) {
        this.connected = connected;
    }


    public ChatFrame(String title, Consumer<String> messageConsumer){

        this.messageConsumer=messageConsumer;
        initChatFrame();
        setTitle(title);

       // initRegistrationPanel();
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
        JButton loginButton= new JButton("Sign in");
        JButton signUpButton= new JButton("Sign up");
        JTextField loginField = new JTextField();
        JLabel loginLabel = new JLabel("Login:");
        JPanel logPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JTextField passwordField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        loginField.setColumns(16);
        passwordField.setColumns(16);
        passwordField.setColumns(16);
        JPanel buttonPanel = new JPanel();

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
        JButton loginButton= new JButton("Sign up");
        JButton signUpButton= new JButton("Back");
        JTextField loginField = new JTextField();
        JTextField nickNameField = new JTextField();
        JLabel nickNameLabel = new JLabel("Nickname:");
        JLabel loginLabel = new JLabel("Login:");
        JPanel logPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel confirmPassPanel = new JPanel();
        JTextField passwordField = new JTextField();
        JTextField confirmPasswordField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        JLabel confirmPasswordLabel = new JLabel("Confirm");
        JPanel nickNamePanel = new JPanel();
        authPanel=new JPanel();
        loginField.setColumns(16);
        passwordField.setColumns(16);
        confirmPasswordField.setColumns(16);
        nickNameField.setColumns(16);
        JPanel buttonPanel = new JPanel();

        panel.add(registrationPanel);
        registrationPanel.setLayout(new GridLayout(7,1));
//row0
        registrationPanel.add(new JLabel());
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
        setBounds(0,0,400,600);
        setConnected(true);
        loginPanel.setVisible(false);
        JPanel mainJpanel = new JPanel();
        add(mainJpanel);
        mainJpanel.setLayout(new BorderLayout());
        mainJpanel.add(topPanel,BorderLayout.CENTER);
        mainJpanel.add(bottomPanel,BorderLayout.SOUTH);
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

    public void setRegistrationServerRequest(JLabel registrationServerRequest) {
        this.registrationServerRequest = registrationServerRequest;
    }
}
