package com.Lesson6.Server.Auth.Controllers;

import com.Lesson6.Server.Auth.AuthentificationData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private final String insertNewAccount = "INSERT INTO 'accounts' ('login', 'pass','nickname') VALUES (?,?,?)";
    private final String getAccount ="SELECT * FROM 'accounts' WHERE login = ? AND pass = ?";
    private final String getAccountNickname = "SELECT nickname FROM 'accounts'";
    private final String clearAccountTable ="DELETE FROM accounts";
    private final String changeAccountNickname ="UPDATE accounts SET nickname=? WHERE nickname=?";

    public void connectToDB()  {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("CLass was not found", e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:ChatDB.s3db");

        } catch (SQLException throwables) {
            throw new RuntimeException("Writing to DB was wrong!", throwables);
        }
        System.out.println("Connection to database successful");
    }

    public synchronized void addUserToDB(String login, String password, String nickname)  {
        try {
            preparedStatement = connection.prepareStatement(insertNewAccount);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,nickname);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Writing to DB was wrong!", throwables);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public synchronized AuthentificationData getUser(String login, String password){
        try {
             preparedStatement = connection.prepareStatement(getAccount);
             preparedStatement.setString(1,login);
             preparedStatement.setString(2,password);
             resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String resultLogin = resultSet.getString("login");
                String resultPassword = resultSet.getString("pass");
                String resultNickname = resultSet.getString("nickname");
                return new AuthentificationData(resultLogin,resultPassword,resultNickname);
            }
            return null;
        } catch (SQLException throwables) {
            throw new RuntimeException("geting user was wrong!", throwables);
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public synchronized void displayAllUsersNickNames(){
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getAccountNickname);
            while (resultSet.next()){
                System.out.println(resultSet.getString("nickname"));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("getting users was wrong!", throwables);
        }finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
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
        } catch (SQLException throwables) {
            throw new RuntimeException("getting users was wrong!", throwables);
        }finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return  nicknames;
    }


    public synchronized void clearDB() {
        try {
            statement = connection.createStatement();
            statement.execute(clearAccountTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException throwables) {
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
        } catch (SQLException throwables) {
            throw new RuntimeException("Writing to DB was wrong!", throwables);
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
