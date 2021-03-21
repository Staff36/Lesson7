package com.Lesson6.Server.Auth.Controllers;

import java.io.*;

public class TextFileController {
    FileWriter writeStream;
    FileReader readStream;
    String fileName;
    public TextFileController(String fileName){
        try {
            this.fileName= fileName;
            writeStream=new FileWriter(fileName,true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean write(String message){
        try {
            writeStream.write(message+"\n");
            writeStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getAllMessages() {
        StringBuilder sb= new StringBuilder();
        char currentChar;
        try {
            readStream= new FileReader(fileName);
            while ((currentChar=(char)readStream.read())!=(char) -1){
                sb.append(currentChar);
            }
            readStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return sb.toString();
    }








}
