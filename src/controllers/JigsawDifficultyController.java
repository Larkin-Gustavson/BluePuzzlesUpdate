package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class JigsawDifficultyController implements Initializable {
    private static String difficulty;
    public static String getDifficulty() {
        return difficulty;
    }
    public static void setDifficulty(String difficulty) {
        JigsawDifficultyController.difficulty = difficulty;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void playEasy(ActionEvent actionEvent) throws Exception {
        setDifficulty("easy");
        // Pick a game at random
        String[] easyGames = {"/Views/jigsaw/easy/jigsaw.fxml", "/Views/jigsaw/easy/skull.fxml"};
        int rand = randomGenerator(easyGames.length) - 1;
        // Load the game
        Parent page = FXMLLoader.load(getClass().getResource(easyGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playMedium(ActionEvent actionEvent) throws Exception {
        setDifficulty("medium");
        // Pick a game at random
        String[] mediumGames = {"/Views/jigsaw/medium/jigsawShyGuy.fxml", "/Views/jigsaw/medium/dooplis.fxml"};
        int rand = randomGenerator(mediumGames.length) - 1;
        // Load the game
        Parent page = FXMLLoader.load(getClass().getResource(mediumGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playHard(ActionEvent actionEvent) throws Exception {
        setDifficulty("hard");
        // Pick a game at random
        String[] hardGames = {"/Views/jigsaw/hard/jigsawEagle.fxml"};
        int rand = randomGenerator(1) - 1;
        // Load the game
        Parent page = FXMLLoader.load(getClass().getResource(hardGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private int randomGenerator(int number) {
        return (int) Math.floor((Math.random() * number + 1));
    }

    /* Go back to Game Selection */
    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}