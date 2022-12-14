package db;

import controllers.JigsawDifficultyController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JigsawLeaderboard {
    public static void insertNewUser(String newUser, String newTime, String difficulty) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String insert = "INSERT INTO JigsawLeaderboard (UserName,Time,Difficulty) VALUES(?, ?, ?)"; // Select statement
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, newUser);
            statement.setString(2, newTime);
            statement.setString(3, difficulty);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("gotta update");
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String update = "Update JigsawLeaderboard SET Time = ?" +
                            "WHERE UserName = ? AND Time >= ? AND DIFFICULTY = ?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, newTime);
            statement.setString(2, newUser);
            statement.setString(3, getTime(newUser, difficulty));
            statement.setString(4, JigsawDifficultyController.getDifficulty());
            statement.executeUpdate();
            // e.printStackTrace();
        }
    }

    public static String getTime(String user, String difficulty) throws SQLException {
        try {
            List<String> records = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String select = "SELECT * FROM JigsawLeaderboard"; // Select statement
            PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
            ResultSet result = statement.executeQuery(); // Initializing all users into result
            while (result.next()) {
                if (result.getString("UserName").equals(user) && result.getString("Difficulty").equals(difficulty))
                    return result.getString("Time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getBestTime(String user, String difficulty) throws SQLException {
        try {
            ArrayList<String> records = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String select = "SELECT * FROM JigsawLeaderboard " +
                            "WHERE UserName=\"" + user + "\" AND Difficulty=\"" + difficulty + "\""; // Select statement
            PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
            ResultSet result = statement.executeQuery(); // Initializing all users into result
            while (result.next()) {
                if (result.getString("UserName").equals(user) &&
                    result.getString("Difficulty").equals(difficulty))
                    return result.getString("Time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> getAllRecords() throws SQLException {
        ArrayList<String> records = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
        String select = "SELECT * FROM JigsawLeaderboard ORDER BY Difficulty, Time"; // Select statement
        PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
        ResultSet result = statement.executeQuery(); // Initializing all users into result

        while (result.next()) {
            records.add(result.getString("UserName"));
            records.add(result.getString("Difficulty"));
            records.add(result.getString("Time"));
        }

        return records;
    }
}