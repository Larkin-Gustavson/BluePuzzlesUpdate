package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeAttackLeaderboard {
    public static void insertNewUser(String username, int points) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String insert = "INSERT INTO TimeAttackLeaderboard (UserName,points) VALUES(?,?)"; // Select statement
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, username);
            statement.setInt(2, points);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("gotta update");
            Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
            String update = "Update TimeAttackLeaderboard SET points=? " +
                            "WHERE Username=? AND points<?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1, points);
            statement.setString(2, username);
            statement.setInt(3, points);

            statement.executeUpdate();
            // e.printStackTrace();
        }
    }

    public static int getHighScore(String username) throws SQLException {
        Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
        String update = "SELECT * FROM TimeAttackLeaderboard WHERE Username=?";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            if (result.getString("Username").equals(username))
                return result.getInt("points");
        }
        return 0;
    }

    public static List<String> getAllRecords() throws SQLException {
        List<String> records = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DbConfig.URL, DbConfig.USERNAME, DbConfig.PASSWORD); // Establishing connection
        String select = "SELECT * FROM TimeAttackLeaderboard ORDER BY points DESC"; // Select statement
        PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
        ResultSet result = statement.executeQuery(); // Initializing all users into result

        while (result.next()) {
            records.add(result.getString("Username"));
            records.add(result.getString("points") + "");
        }

        return records;
    }
}