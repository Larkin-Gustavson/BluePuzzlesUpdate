package controllers;

import db.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    public static void insertNewUser(String game, String newUser, String newTime, String difficulty) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String insert = "INSERT INTO " + game + " (UserName,Time,Difficulty) VALUES(?, ?, ?)"; // Select statement
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, newUser);
            statement.setString(2, newTime);
            statement.setString(3, difficulty);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("gotta update");
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String update = "Update " + game + " SET Time = ?" +
                            "WHERE UserName = ? AND Difficulty = ? AND Time > ?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, newTime);
            statement.setString(2, newUser);
            statement.setString(3, JigsawDifficultyController.getDifficulty());
            statement.setString(4, newTime);

            statement.executeUpdate();
            // e.printStackTrace();
        }
    }

    public static String getTime(String game, String username, String difficulty) throws SQLException {
        try {
            List<String> records = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String select = "SELECT * FROM " + game; // Select statement
            PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
            ResultSet result = statement.executeQuery(); // Initializing all users into result
            while (result.next()) {
                if (result.getString("UserName").equals(username) && result.getString("Difficulty").equals(difficulty))
                    return result.getString("Time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getBestTime(String game, String username, String difficulty) {
        try {
            List<String> records = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String select = "SELECT * FROM  " + game +
                            " WHERE UserName=\"" + username + "\" AND Difficulty=\"" + difficulty + "\""; // Select statement
            PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
            ResultSet result = statement.executeQuery(); // Initializing all users into result
            while (result.next()) {
                if (result.getString("UserName").equals(username) &&
                    result.getString("Difficulty").equals(difficulty))
                    return result.getString("Time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> getAllRecords(String game) throws SQLException {
        List<String> al = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
        String select = "SELECT * FROM " + game + " ORDER BY Difficulty, Time"; // Select statement
        PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
        ResultSet result = statement.executeQuery(); // Initializing all users into result

        while (result.next()) {
            al.add(result.getString("UserName"));
            al.add(result.getString("Difficulty"));
            al.add(result.getString("Time"));
        }

        return al;
    }
}
