package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
// Test
public class HangmanDifficultyController {
    private static String difficulty;
    @FXML
    private Button easyButton;
    @FXML
    private Button backButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;

    public static String getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(String diff) {
        difficulty = diff;
    }

    @FXML
    void clickEasyButton(MouseEvent event) throws Exception {
        setDifficulty("easy");
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangman.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickMediumButton(MouseEvent event) throws Exception {
        setDifficulty("medium");
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangman.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickHardButton(MouseEvent event) throws Exception {
        setDifficulty("hard");
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
