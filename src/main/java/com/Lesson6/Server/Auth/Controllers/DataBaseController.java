package com.Lesson6.Server.Auth.Controllers;

import com.Lesson6.Server.Auth.AuthentificationData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {

    private static final Logger LOGGER = LogManager.getLogger(DataBaseController.class);
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private final String insertNewAccount = "INSERT INTO 'accounts' ('login', 'pass','nickname') VALUES (?,?,?)";
    private final String getAccount ="SELECT * FROM 'accounts' WHERE login = ? AND pass = ?";
    private final String getAccountNickname = "SELECT nickname FROM 'accounts'";
    private final String clearAccountTable ="DELETE FROM accounts";
    private final String changeAccountNickname ="UPDATE accounts SET nickname=? WHERE nickname=?";
    private final String closeConnectionErrorMsg ="SQLException when we try to close Statement, ";
    private final String writingErrorMsg ="Writing to DB was wrong!";

    public void connectToDB()  {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            LOGGER.error("CLass of JDBC was not found ", e);
            throw new RuntimeException("CLass was not found", e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:ChatDB.s3db");

        } catch (SQLException throwables) {
            LOGGER.error("SQL Exception when we can try to get connection!", throwables);
            throw new RuntimeException("Writing to DB was wrong!", throwables);
        }
        LOGGER.debug("Connection to database successful");
    }

    public synchronized void addUserToDB(String login, String password, String nickname)  {
        try {
            preparedStatement = connection.prepareStatement(insertNewAccount);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,nickname);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            LOGGER.error(writingErrorMsg, throwables);
            throw new RuntimeException("Writing to DB was wrong!", throwables);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                LOGGER.error(closeConnectionErrorMsg, throwables);
                throwables.printStackTrace();
            }
        }
        LOGGER.debug("User "+ nickname +" has already added into DB");
    }

    public synchronized AuthentificationData getUser(String login, String password){
        try {
             String resultNickname = null;
             preparedStatement = connection.prepareStatement(getAccount);
             preparedStatement.setString(1,login);
             preparedStatement.setString(2,password);
             resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String resultLogin = resultSet.getString("login");
                String resultPassword = resultSet.getString("pass");
                resultNickname = resultSet.getString("nickname");
                return new AuthentificationData(resultLogin,resultPassword,resultNickname);
            }
            LOGGER.debug("User " + resultNickname + " has already added into DB");
            return null;
        } catch (SQLException throwables) {
            LOGGER.error("When we try to get User ", throwables);
            throw new RuntimeException("geting user was wrong!", throwables);
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                LOGGER.error(closeConnectionErrorMsg, throwables);
                throwables.printStackTrace();
            }
        }
    }

    public synchronized void displayAllUsersNickNames(){
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getAccountNickname);
            while (resultSet.next()){
                LOGGER.debug(resultSet.getString("nickname"));
            }

        } catch (SQLException throwables) {
            LOGGER.error("When we try to get User ", throwables);
            throw new RuntimeException("getting users was wrong!", throwables);
        }finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
                LOGGER.error(closeConnectionErrorMsg, throwables);
                throwables.printStackTrace();
            }
        }
    }

    public synchronized List<String> getAllUsersNickNames(){
        List<String> nicknames = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getAccountNickname);
            while (resultSet.next()){
                nicknames.add(resultSet.getString("nickname"));

            }
            LOGGER.debug("Nicknames has already retrieved from DB");
        } catch (SQLException throwable) {
            LOGGER.error("When we try to get UserNickname ", throwable);
            throw new RuntimeException("getting users was wrong!", throwable);
        }finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
                LOGGER.error(closeConnectionErrorMsg, throwables);
                throwables.printStackTrace();
            }
        }
        return  nicknames;
    }


    public synchronized void clearDB() {
        try {
            statement = connection.createStatement();
            statement.execute(clearAccountTable);
            LOGGER.debug("Data Base has already cleaned");
        } catch (SQLException throwable) {
            LOGGER.error("When we try to clean up database ", throwable);
            throwable.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
                LOGGER.error(closeConnectionErrorMsg, throwables);
                throwables.printStackTrace();
            }
        }
    }

    public synchronized void changeNickname(String currentNickname, String desiredNickname){
        try {
            preparedStatement = connection.prepareStatement(changeAccountNickname);
            preparedStatement.setString(1,desiredNickname);
            preparedStatement.setString(2,currentNickname);
            preparedStatement.executeUpdate();
            LOGGER.debug("User with nickname " + currentNickname + " has already change it on " + desiredNickname);
        } catch (SQLException throwable) {
            LOGGER.error(writingErrorMsg, throwable);
            throw new RuntimeException("Writing to DB was wrong!", throwable);
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwable) {
                LOGGER.error(closeConnectionErrorMsg, throwable);
                throwable.printStackTrace();
            }
        }
    }

}
