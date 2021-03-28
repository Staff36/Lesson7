package com.Lesson6.Server.Auth;

import com.Lesson6.Server.Auth.Controllers.DataBaseController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class AuthentificatonService {
private DataBaseController dataBaseController= new DataBaseController();
private static final Logger LOGGER = LogManager.getLogger(AuthentificatonService.class);



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
