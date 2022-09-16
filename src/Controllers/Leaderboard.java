package Controllers;

import java.sql.*;
import java.util.ArrayList;


public class Leaderboard {

    private static final String URL = "jdbc:mysql://bluepuzzles.c6g1bhjsrnsm.us-east-2.rds.amazonaws.com/BLUE_PUZZLES";
    private static final String USERNAME = "bluepuzzles";
    private static final String PASSWORD = "bluepuzzles123";

    public static void insertNewUser(String game, String newUser, String newTime, String difficulty) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
            String insert = "INSERT INTO " + game + " (UserName,Time,Difficulty) VALUES(?, ?, ?)"; // Select statement
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, newUser);
            statement.setString(2, newTime);
            statement.setString(3, difficulty);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("gotta update");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
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
            ArrayList<String> records = new ArrayList<>();
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
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

    public static String getBestTime(String game, String username, String difficulty) throws SQLException {
        try {
            ArrayList<String> records = new ArrayList<>();
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
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

    public static ArrayList<String> getAllRecords(String game) throws SQLException {
        ArrayList<String> al = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
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
