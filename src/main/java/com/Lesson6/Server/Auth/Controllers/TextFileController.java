package com.Lesson6.Server.Auth.Controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileController {

    private static final Logger LOGGER = LogManager.getLogger(TextFileController.class);
    private FileWriter writeStream;
    private FileReader readStream;
    private final String fileName;

    public TextFileController(String fileName){
        this.fileName = fileName;
        try {
            writeStream = new FileWriter(fileName,true);
            LOGGER.debug("FileWriter has already created");
        } catch (FileNotFoundException e) {
            LOGGER.error("Something wrong with file: ", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("Something wrong with Write Stream: ", e);
            e.printStackTrace();
        }
    }
//writeStream не закрываю так как он постоянно используется
    public synchronized boolean write(String message){
        try {
            writeStream.write(message+"\n");
            writeStream.flush();
            LOGGER.debug("Msg has already added to file");
            return true;
        } catch (IOException e) {
            LOGGER.error("Something wrong with  Write Stream: ", e);
            e.printStackTrace();
            return false;
        }
    }

    public synchronized String getAllMessages() throws IOException {
        StringBuilder sb = new StringBuilder();
        char currentChar;
        try {
            readStream = new FileReader(fileName);
            LOGGER.debug("FileReader has already created");
            while ((currentChar = (char)readStream.read()) != (char) -1){
                sb.append(currentChar);
            }
        } catch (IOException e) {
            LOGGER.error("Something wrong with  Write Stream: ", e);
            e.printStackTrace();
        } finally {
            readStream.close();
            LOGGER.debug("FileReader has already closed");
        }
       return sb.toString();
    }








}
