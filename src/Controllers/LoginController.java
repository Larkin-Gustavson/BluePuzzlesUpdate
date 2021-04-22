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

public class LoginController implements Initializable {

    @FXML
    AnchorPane signUpPane;
    @FXML
    TextField userField, loginUser;
    @FXML
    PasswordField passwordField, rePassword, loginPassword;
    @FXML
    Label outputLabel, loginMessage;

    //Stores the user in here for the program
    public static String user;

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

    @FXML /*Move onto main menu*/
    public void login(ActionEvent event) throws Exception {
        if (UserAccount.userExist(loginUser.getText()) && UserAccount.correctPassword(loginUser.getText(), loginPassword.getText())) {
            user = loginUser.getText(); //Stores username
            Parent page = FXMLLoader.load(getClass().getResource("/Views/mainmenu.fxml"));
            Scene scene = new Scene(page, 900, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            loginMessage.setText("Username and password do not match!");
        }
    }

    /*Sign up for new Account*/
    public void SignUp(ActionEvent actionEvent) {
        signUpPane.setVisible(true);

    }

    /*Cancel account creation*/
    public void goBack(ActionEvent actionEvent) {
        signUpPane.setVisible(false);
    }

    /*Create new account if password and username are valid*/
    public void signUpUser(ActionEvent actionEvent) throws SQLException {
        if (validatePassword(passwordField.getText()) && validateUserName(userField.getText())) {
            UserAccount.insertNewUser(userField.getText(), passwordField.getText());
            signUpPane.setVisible(false);
        }

    }

    /*Password validation*/
    public boolean validatePassword(String str) {
        String output = "";
        char ch;
        boolean capitalFlag = false; //password contains capital letter
        boolean lowerCaseFlag = false; //password contains lowercase letter
        boolean numberFlag = false; //password contains number
        boolean samePasswordFlag = false; //passwords match
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (passwordField.getText().equals(rePassword.getText()))
                samePasswordFlag = true;
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag && samePasswordFlag) {
                outputLabel.setText("");
                return true;
            }
        }
        if (!capitalFlag)
            output += "Username needs a capital letter!\n";
        if (!lowerCaseFlag)
            output += "Username needs a lowercase letter!\n";
        if (!numberFlag)
            output += "Username needs a number!\n";
        if (!samePasswordFlag) {
            output += "Passwords do not match!\n";
        }

        outputLabel.setText(output);
        outputLabel.setTextFill(Color.RED);
        passwordField.setText("");
        rePassword.setText("");
        return false;

    }

    public boolean validateUserName(String userName) throws SQLException {
        String output = "";
        try {
            if (UserAccount.userExist(userName)) {
                output += "UserName already exists!";
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
