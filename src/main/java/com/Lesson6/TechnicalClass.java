package com.Lesson6;

import com.Lesson6.Server.Auth.DataBaseController;

public class TechnicalClass {
    public static void main(String[] args) {
        DataBaseController.connectToDB();
        DataBaseController.displayAllUsersNickNames();
        DataBaseController.clearDB();
        DataBaseController.addUserToDB("l1","p1","Zhora");
        DataBaseController.addUserToDB("l2","p2","Semen");
        DataBaseController.addUserToDB("l13","p3","Vasya");
        DataBaseController.displayAllUsersNickNames();
        DataBaseController.closeAllConnections();
    }
}
