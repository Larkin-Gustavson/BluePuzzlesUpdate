package Controllers;

import DB.UserAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
public class LoginController implements Initializable {

    // Stores the user in here for the program
    public static String user;
    @FXML
    AnchorPane signUpPane;
    @FXML
    TextField userField, loginUser;
    @FXML
    PasswordField passwordField, rePassword, loginPassword;
    @FXML
    Label outputLabel, loginMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Tooltip usernameTooltip = new Tooltip();
        usernameTooltip.setText("Username must have atleast \n" +
                "- 8 characters\n" +
                "- 1 Capital Letter\n" +
                "- 1 Lowercase Letter\n" +
                "- 1 numerical character");
        userField.setTooltip(usernameTooltip);
    }

    @FXML /* Move onto main menu */
    public void login(ActionEvent event) throws Exception {
        if (UserAccount.userExist(loginUser.getText()) && UserAccount.correctPassword(loginUser.getText(), loginPassword.getText())) { // if the user entered a correct username and password, do the following
            user = loginUser.getText(); // Stores the username
            Parent page = FXMLLoader.load(getClass().getResource("/Views/mainmenu.fxml")); // load the main menu after a successful login
            Scene scene = new Scene(page, 900, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (loginUser.getText().equalsIgnoreCase("") || loginPassword.getText().equalsIgnoreCase("")) {
            loginMessage.setText("Please enter a username or a password");
        } else { // if the user entered an incorrect username or password, do the following
            loginUser.clear(); // clear whatever was in the the username field
            loginPassword.clear(); // clear whatever was in the password field
            loginMessage.setTextFill(Color.RED);
            loginMessage.setText("Sorry, incorrect username or password!");
        }
    }

    /* Sign up for new Account */
    public void SignUp(ActionEvent actionEvent) {
        signUpPane.setVisible(true);
    }

    /* Cancel account creation */
    public void goBack(ActionEvent actionEvent) {
        signUpPane.setVisible(false);
    }

    /* Create new account if password and username are valid */
    public void signUpUser(ActionEvent actionEvent) throws SQLException {
        if (validatePassword(passwordField.getText()) && validateUserName(userField.getText())) {
            UserAccount.insertNewUser(userField.getText(), passwordField.getText());
            signUpPane.setVisible(false);
        }
    }

    /* Password validation */

    /**
     *
     * @param str the password the user put into the password field.
     * @return true if the password the user put into the password field is valid.
     *         <br></br>
     *         false if the password the user put into the password field is not valid.
     */
    public boolean validatePassword(String str) {
        /* Regular expression to allow at minimum one digit, at minimum lower case letter,
        *  at least one upper case letter, does not allow special characters
        *  (!@#$%^&*(),.<>+?|{}\=~`), does not allow any sort of whitespace,
        *  and the passwords need to be between 8 - 15 characters long.
        *
        */
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=[^!@#$%^&*(),.<>+?|{}\\\\=~`]+$)(?=\\S+$).{8,15}$";

        Pattern validPasswordPattern = Pattern.compile(regex); // compile the regex as a pattern or template that all valid passwords must adhere to

        Matcher passwordMatcher = validPasswordPattern.matcher(passwordField.getText()); // get the text stored in the password field and make sure that the password field matches the template for a valid password
        Matcher rePasswordMatcher = validPasswordPattern.matcher(rePassword.getText()); // get the text stored in the confirm password field make sure that the confirm password field matches the template for a valid password

        // see if the passwords in both fields match the regular expression pattern
        boolean isPasswordMatchingPattern = passwordMatcher.find();
        boolean isRePasswordMatchingPattern = rePasswordMatcher.find();

        /*
            If the password and the confirm password match the criteria in the
            regex expression (must have at least 1 capital letter, 1 lowercase letter,
            no special characters, no whitespace, and between 8-15 characters long.

            If the entered passwords in the password field and the Re-enter password
            field are the same, then login (return true).

            Else if the passwords entered are not the same, report that the user did not enter
            the same password in both fields (return false).

            Else report to the user that they did not enter a password that meets the
            acceptable criteria for a valid password (return false).
         */
        if (isPasswordMatchingPattern == true && isRePasswordMatchingPattern == true && passwordField.getText().equals(rePassword.getText())) { // if the password both matches the templated pattern for a valid password and that the passwords entered in both the password field and the confirm password equal each other, then return true
            return true;
        } else if (!passwordField.getText().equals(rePassword.getText())) {
            outputLabel.setText(""); // used to clear the output text if the user had a previous message shown on screen
            outputLabel.setTextFill(Color.RED);
            outputLabel.setText("Your passwords do not match!");
            passwordField.clear(); // clear whatever the user had entered in the password field for convenience of the user
            rePassword.clear(); // clear whatever the user had entered in the confirm password field for convenience of the user
            return false;
        }
        outputLabel.setTextFill(Color.RED);
        outputLabel.setText("Your password must contain \n" +
                "at least one capital letter, \n" +
                "at least one lowercase letter, \n" +
                "at least one number, \n" +
                "no whitespaces, \n" +
                "no special characters, \n"+
                "(!@#$%^&*(),.<>+?|{}\\\\=~`) \n" +
                "and contains 8 - 15 characters."
        );
        passwordField.clear(); // clear whatever the user had entered in the password field for convenience of the user
        rePassword.clear(); // clear whatever the user had entered in the confirm password field for convenience of the user
        return false;
    }

    /**
     *
     * @param userName the username the user passed into the field.
     * @return true - if the username is valid (if it doesn't exist already).
     *          <br></br>
     *          false - if the username is not valid (if the username already exists).
     * @throws SQLException if the query is not able to execute.
     */
    public boolean validateUserName(String userName) throws SQLException {
        String output = "";
        try {
            if (UserAccount.userExist(userName)) {
                output += "Username already exists!";
                outputLabel.setText(output);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
