package DB;

import Controllers.JigsawDifficultyController;

import java.sql.*;
import java.util.ArrayList;

public class TimeAttackLeaderboard {

    private static String url = "jdbc:mysql://bluepuzzles.c6g1bhjsrnsm.us-east-2.rds.amazonaws.com/BLUE_PUZZLES";
    private static String username = "bluepuzzles";
    private static String password = "bluepuzzles123";

    public static void insertNewUser(String user, int points) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password); //Establishing connection
            String insert = "INSERT INTO TimeAttackLeaderboard (UserName,points) VALUES(?,?)"; //Select statement
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, user);
            statement.setInt(2, points);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("gotta update");
            Connection connection = DriverManager.getConnection(url, username, password); //Establishing connection
            String update = "Update TimeAttackLeaderboard SET points=? " +
                    "WHERE Username=? AND points<?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1, points);
            statement.setString(2, user);
            statement.setInt(3, points);

            statement.executeUpdate();
            //e.printStackTrace();
        }
    }

    public static int getHighScore(String user) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password); //Establishing connection
        String update = "SELECT * FROM TimeAttackLeaderboard WHERE Username=?";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, user);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            if (result.getString("Username").equals(user))
                return result.getInt("points");
        }
        return 0;
    }

    public static ArrayList<String> getAllRecords() throws SQLException {
        ArrayList<String> al = new ArrayList<>();
        Connection connection = DriverManager.getConnection(url, username, password); //Establishing connection
        String select = "SELECT * FROM TimeAttackLeaderboard ORDER BY points DESC"; //Select statment
        PreparedStatement statement = connection.prepareStatement(select); //Prepared Statement
        ResultSet result = statement.executeQuery(); //Initializing all users into result

        while (result.next()) {
            al.add(result.getString("Username"));
            al.add(result.getString("points") + "");
        }

        return al;

    }

}
