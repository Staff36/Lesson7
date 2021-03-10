package com.Lesson6.Server.Auth;

import java.util.Objects;

public class AuthentificationData {
    private String login;
    private String password;
    private String nickName;

    public AuthentificationData(String login, String password, String nickName) {
        this.login = login;
        this.password = password;
        this.nickName = nickName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthentificationData{" +
                "nickName='" + nickName + '\'' +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthentificationData that = (AuthentificationData) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(nickName, that.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, nickName);
    }
}
