package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private MenuItem aboutButton;
    @FXML
    private AnchorPane anchorPaneMM;
    @FXML
    private Button leaderboardButton;
    @FXML
    private ImageView leftImage;
    @FXML
    private Button logOut;
    @FXML
    private Button playButton;
    @FXML
    private ImageView rightImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void play(ActionEvent event) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void test(MouseEvent mouseEvent) {

    }

    public void showImage(MouseEvent mouseEvent) {
        leftImage.setVisible(true);
        rightImage.setVisible(true);
    }

    public void hideImage(MouseEvent mouseEvent) {
        leftImage.setVisible(false);
        rightImage.setVisible(false);
    }

    @FXML
    void aboutButtonClick(ActionEvent event) {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setResizable(false);
        aboutDialog.setTitle("About");
        aboutDialog.setHeaderText("Blue Puzzles");
        aboutDialog.setContentText("This application, was made as a Senior Project." +
                                   " It features games such as Hangman, Jigsaw, Sudoku, Sudoku X, Memory, TimeAttack, and Tents." +
                                   " All games have easy, medium, and hard difficulties." +
                                   " Along with random level generation for the games. A user can create an account" +
                                   " that allows there stats across all of the games to be saved" +
                                   " and added to a leaderboard to compare against other players." +
                                   " This application/project code was created by Larkin Gustavson, Chris Gibbone, and Abdulah Naderi.");
        aboutDialog.showAndWait();
    }
    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showLeaderboard(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/views/leaderboards.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}