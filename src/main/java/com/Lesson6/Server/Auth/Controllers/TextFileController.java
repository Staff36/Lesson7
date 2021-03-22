package com.Lesson6.Server.Auth.Controllers;

import java.io.*;

public class TextFileController {

    private FileWriter writeStream;
    private FileReader readStream;
    private final String fileName;

    public TextFileController(String fileName){
        this.fileName = fileName;
        try {
            writeStream = new FileWriter(fileName,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//writeStream не закрываю так как он постоянно используетсяж
    public synchronized boolean write(String message){
        try {
            writeStream.write(message+"\n");
            writeStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized String getAllMessages() throws IOException {
        StringBuilder sb = new StringBuilder();
        char currentChar;
        try {
            readStream = new FileReader(fileName);
            while ((currentChar = (char)readStream.read()) != (char) -1){
                sb.append(currentChar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readStream.close();
        }
       return sb.toString();
    }








}
