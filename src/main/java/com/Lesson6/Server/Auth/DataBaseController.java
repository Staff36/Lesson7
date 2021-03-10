package com.Lesson6.Server.Auth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    public static void connectToDB()  {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("CLass was not found", e);
        }
        try {
            connection= DriverManager.getConnection("jdbc:sqlite:ChatDB.s3db");
            statement=connection.createStatement();
        } catch (SQLException throwables) {
            throw new RuntimeException("Writing to DB was wrong!", throwables);
        }
        ;
        System.out.println("Connection to database successful");
    }
    public synchronized static void addUserToDB(String login, String password, String nickname)  {
        try {
            preparedStatement=connection.prepareStatement("INSERT INTO 'accounts' ('login', 'pass','nickname') VALUES (?,?,?)");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,nickname);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Writing to DB was wrong!", throwables);
        }

    }
    public synchronized static AuthentificationData getUser(String login, String password){
        try {
            resultSet= statement.executeQuery("SELECT * FROM 'accounts' WHERE login='"+login+"' AND pass='"+password+"'");
            while (resultSet.next()){
                String resultLogin=resultSet.getString("login");
                String resultPassword=resultSet.getString("pass");
                String resultNickname=resultSet.getString("nickname");
                return new AuthentificationData(resultLogin,resultPassword,resultNickname);
            }
            return null;
        } catch (SQLException throwables) {
            throw new RuntimeException("geting user was wrong!", throwables);
        }
    }
    public synchronized static void displayAllUsersNickNames(){

        try {
            resultSet=statement.executeQuery("SELECT nickname FROM 'accounts'");
            while (resultSet.next()){
                System.out.println(resultSet.getString("nickname"));
            }

        } catch (SQLException throwables) {
            throw new RuntimeException("getting users was wrong!", throwables);
        }
    }

    public synchronized static List<String> getAllUsersNickNames(){
        List<String> nicknames= new ArrayList<>();
        try {
            resultSet=statement.executeQuery("SELECT nickname FROM 'accounts'");
            while (resultSet.next()){
                nicknames.add(resultSet.getString("nickname"));
            }

        } catch (SQLException throwables) {
            throw new RuntimeException("getting users was wrong!", throwables);
        }
        return  nicknames;
    }
    public static void closeAllConnections(){
        try {
            resultSet.close();
            if (preparedStatement!=null)
                preparedStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throw new RuntimeException("Closing connection was wrong!", throwables);
        }
    }
    public synchronized static void clearDB(){
        try {
            statement.execute("DELETE FROM accounts");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
