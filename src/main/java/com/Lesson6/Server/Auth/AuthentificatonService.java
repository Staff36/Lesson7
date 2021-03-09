package com.Lesson6.Server.Auth;

import java.util.Set;

public class AuthentificatonService {
    private static final Set<AuthentificationData> clientsDate= Set.of(
            new AuthentificationData("l1","p1", "First"),
            new AuthentificationData("l2","p2", "Second"),
            new AuthentificationData("l3","p3", "Third")
    );

    public AuthentificationData findUserByCredentials(String login, String password){
        for (AuthentificationData client:clientsDate) {
            if (client.getLogin().equals(login)&&client.getPassword().equals(password)){
                return client;
            }
        }
        return null;
    }
}
