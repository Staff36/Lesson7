package com.Lesson6.Server.Auth;

import com.Lesson6.Server.Auth.Controllers.DataBaseController;

import java.util.List;

public class AuthentificatonService {
private DataBaseController dataBaseController= new DataBaseController();

    public AuthentificationData findUserByCredentials(String login, String password){
        return dataBaseController.getUser(login, password);
    }

    public boolean nicknameIsFree(String nickname){
        List<String> nicknames = dataBaseController.getAllUsersNickNames();
        for (String name : nicknames) {
            if (name.equals(nickname))
                return false;
        }
        return true;
    }
}
