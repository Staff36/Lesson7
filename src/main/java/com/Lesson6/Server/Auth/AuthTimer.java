package com.Lesson6.Server.Auth;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AuthTimer {
    private static final Logger LOGGER = LogManager.getLogger(AuthTimer.class);
    private final long startTime;
    private long deltaTime;
    private final long TIME_ON_AUTH;

    public AuthTimer(long TIME_ON_AUTH) {
        this.startTime = System.currentTimeMillis();
        this.TIME_ON_AUTH = TIME_ON_AUTH;
        LOGGER.debug("Auth timer has already started");
    }

    public boolean checkTheTimeForAuth(){
        deltaTime = System.currentTimeMillis()-startTime;
        return TIME_ON_AUTH > deltaTime;
    }

}
