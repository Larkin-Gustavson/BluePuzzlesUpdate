package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccount {
    private static final String URL = "jdbc:mysql://bluepuzzles.c6g1bhjsrnsm.us-east-2.rds.amazonaws.com/BLUE_PUZZLES";
    private static final String USERNAME = "bluepuzzles";
    private static final String PASSWORD = "bluepuzzles123";

    public UserAccount() {

    }

    public static ArrayList<String> getUsers() throws SQLException {
        ArrayList<String> records = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
        String select = "SELECT * FROM Accounts;"; // Select statement
        PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
        ResultSet result = statement.executeQuery(); // Initializing all users into result
        /* Displaying all users */
        while (result.next()) {
            records.add(result.getString("UserName"));
        }
        return records;
    }

    public static void insertNewUser(String newUser, String newPassword) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
        String insert = "INSERT INTO Accounts (UserName,Password) VALUES(?,?);"; // Select statement
        PreparedStatement statement = connection.prepareStatement(insert);
        try {
            if (userExist(newUser))
                System.out.println("Username already exists");
            else {
                statement.setString(1, newUser);
                String pass = AES.encrypt(newPassword, "allme");
                statement.setString(2, pass);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }


    public static boolean userExist(String newUser) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
        String select = "SELECT * FROM Accounts;"; // Select statement
        PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
        ResultSet result = statement.executeQuery(); // Initializing all users into result
        while (result.next()) {
            try {
                if (result.getString("UserName").equalsIgnoreCase(newUser))
                    return true;
            } catch (NullPointerException e) {
                break;
            }
        }
        return false;
    }

    public static boolean correctPassword(String username, String password) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establishing connection
            String select = "SELECT * FROM Accounts;"; // Select statement
            PreparedStatement statement = connection.prepareStatement(select); // Prepared Statement
            ResultSet result = statement.executeQuery(); // Initializing all users into result
            while (result.next()) {
                if (result.getString("UserName").equals(username)) {
                    String inputPassword = AES.encrypt(password, "allme");
                    String dePass = result.getString("Password");
                    if (dePass.equals(inputPassword))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}