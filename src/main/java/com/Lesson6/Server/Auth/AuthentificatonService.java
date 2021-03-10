package com.Lesson6.Server.Auth;

import java.util.List;

public class AuthentificatonService {
//    private static final Set<AuthentificationData> clientsDate= Set.of(
//            new AuthentificationData("l1","p1", "First"),
//            new AuthentificationData("l2","p2", "Second"),
//            new AuthentificationData("l3","p3", "Third")
//    );

    public AuthentificationData findUserByCredentials(String login, String password){

        return DataBaseController.getUser(login, password);
//        for (AuthentificationData client:clientsDate) {
//            if (client.getLogin().equals(login)&&client.getPassword().equals(password)){
//                return client;
//            }
//        }
//        return null;
    }

    public boolean nicknameIsFree(String nickname){
        List<String> nicknames =DataBaseController.getAllUsersNickNames();
        for (String name:nicknames) {
            if (name.equals(nickname))
                return false;
        }
        return true;
    }
}
