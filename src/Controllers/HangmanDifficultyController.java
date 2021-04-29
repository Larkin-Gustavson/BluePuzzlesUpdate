package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HangmanDifficultyController {
    private static String difficultyLevel = "";
    @FXML
    private Button easyButton;
    @FXML
    private Button backButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;

    public static String getDifficultyLevel() {
        return difficultyLevel;
    }

    public static void setDifficultyLevel(String difLevel) {
        difficultyLevel = difLevel;
    }


    @FXML
    void clickEasyButton(MouseEvent event) throws Exception {
        setDifficultyLevel("Easy");
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangman.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickMediumButton(MouseEvent event) throws Exception {
        setDifficultyLevel("Medium");
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangman.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickHardButton(MouseEvent event) throws Exception {
        setDifficultyLevel("Hard");
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangman.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backButtonClick(MouseEvent event) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
