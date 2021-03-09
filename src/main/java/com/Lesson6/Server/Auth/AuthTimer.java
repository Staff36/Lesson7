package com.Lesson6.Server.Auth;

public class AuthTimer {
    private final long startTime;
    private long deltaTime;
    private final long TIME_ON_AUTH;

    public AuthTimer(long TIME_ON_AUTH) {
        this.startTime = System.currentTimeMillis();
        this.TIME_ON_AUTH=TIME_ON_AUTH;
    }
    public boolean checkTheTimeForAuth(){
        deltaTime=System.currentTimeMillis()-startTime;
        return TIME_ON_AUTH>deltaTime?true:false;
    }

}
