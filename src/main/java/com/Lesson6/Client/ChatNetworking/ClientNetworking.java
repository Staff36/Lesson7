package com.Lesson6.Client.ChatNetworking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientNetworking implements ChatNetworkingInterface {
    private Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ClientNetworking(String host, int port) {
        try {
            this.socket = new Socket(host,port);
            in= new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("SWW during establishing", e);
        }
    }

public void send(String message){
    try {
        out.writeUTF(message);
    } catch (IOException e) {
        throw new RuntimeException("SWW dutring send", e);
    }
}

public String receive(){
    try {
        return in.readUTF();
    } catch (IOException e) {
        throw new RuntimeException("SWW dutring receive", e);
    }
}
}

