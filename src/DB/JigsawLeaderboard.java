package DB;

import Controllers.JigsawController;
import Controllers.JigsawDifficultyController;

import java.sql.*;
import java.util.*;


public class JigsawLeaderboard {

    private static String url = "jdbc:mysql://bluepuzzles.c6g1bhjsrnsm.us-east-2.rds.amazonaws.com/BLUE_PUZZLES";
    private static String username = "bluepuzzles";
    private static String password = "bluepuzzles123";

    public static void insertNewUser(String newUser, String newTime, String Difficulty) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password); //Establishing connection
            String insert = "INSERT INTO JigsawLeaderboard (UserName,Time,Difficulty) VALUES(?,?,?)"; //Select statement
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, newUser);
            statement.setString(2, newTime);
            statement.setString(3, Difficulty);
            statement.executeUpdate();
        } catch (SQLException e) {
            Connection connection = DriverManager.getConnection(url, username, password); //Establishing connection
            String update = "Update JigsawLeaderboard SET Time=?" +
                    "WHERE UserName=? AND Time >= ? AND DIFFICULTY=?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, newTime);
            statement.setString(2, newUser);
            statement.setString(3, getTime(newUser));
            statement.setString(4, JigsawDifficultyController.difficulty);
            statement.executeUpdate();
            //e.printStackTrace();
        }
    }

    public static String getTime(String user) throws SQLException {
        try {
            ArrayList<String> records = new ArrayList<>();
            Connection connection = DriverManager.getConnection(url, username, password); //Establishing connection
            String select = "SELECT * FROM JigsawLeaderboard"; //Select statment
            PreparedStatement statement = connection.prepareStatement(select); //Prepared Statement
            ResultSet result = statement.executeQuery(); //Initializing all users into result
            while (result.next()) {
                if (result.getString("UserName").equals(user))
                    return result.getString("Time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}