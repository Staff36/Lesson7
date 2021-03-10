package com.Lesson6.Server.Auth;

import java.util.List;

public class AuthentificatonService {


    public AuthentificationData findUserByCredentials(String login, String password){
        return DataBaseController.getUser(login, password);
    }

    public boolean nicknameIsFree(String nickname){
        List<String> nicknames = DataBaseController.getAllUsersNickNames();
        for (String name : nicknames) {
            if (name.equals(nickname))
                return false;
        }
        return true;
    }
}
