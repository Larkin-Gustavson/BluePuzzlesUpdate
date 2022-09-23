package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSelectController implements Initializable {
    @FXML
    private ImageView sudokuImage;
    @FXML
    private ImageView sudokuImage2;
    @FXML
    private ImageView hangmanImage;
    @FXML
    private ImageView hangmanImage2;
    @FXML
    private ImageView shyguy1;
    @FXML
    private ImageView shyguy2;
    @FXML
    private ImageView tents1;
    @FXML
    private ImageView tents2;
    @FXML
    private ImageView timeAttack1;
    @FXML
    private ImageView timeAttack2;
    @FXML
    private ImageView memory1;
    @FXML
    private ImageView memory2;
    @FXML
    private Text hangmanText;
    @FXML
    private Text sudokuText;
    @FXML
    private Text jigsawText;
    @FXML
    private Text tentsText;
    @FXML
    private Text memoryText;
    @FXML
    private Text timeAttackText;
    private static int totalPoints;
    public static int getTotalPoints() {
        return totalPoints;
    }
    public static void setTotalPoints(int totalPoints) {
        GameSelectController.totalPoints = totalPoints;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /* Sudoku Methods */
    public void playSudoku(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/sudokuboard.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showImage(MouseEvent mouseEvent) {
        sudokuImage.setVisible(true);
        sudokuImage2.setVisible(true);
        sudokuText.setVisible(true);
    }

    public void hideImage(MouseEvent mouseEvent) {
        sudokuImage.setVisible(false);
        sudokuImage2.setVisible(false);
        sudokuText.setVisible(false);
    }

    /* Hangman methods */
    public void playHangman(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangmanDifficulty.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showHangManImage(MouseEvent mouseEvent) {
        hangmanImage.setVisible(true);
        hangmanImage2.setVisible(true);
        hangmanText.setVisible(true);
    }

    public void hideHangManImage(MouseEvent mouseEvent) {
        hangmanImage.setVisible(false);
        hangmanImage2.setVisible(false);
        hangmanText.setVisible(false);
    }

    /* Jigsaw Methods */
    public void playJigsaw(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/jigsawDifficulty.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showJigsaw(MouseEvent mouseEvent) {
        shyguy1.setVisible(true);
        shyguy2.setVisible(true);
        jigsawText.setVisible(true);
    }

    public void hideJigsaw(MouseEvent mouseEvent) {
        shyguy1.setVisible(false);
        shyguy2.setVisible(false);
        jigsawText.setVisible(false);
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/mainmenu.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playTents(ActionEvent actionEvent) throws Exception {
        String[] games = {"/Views/tents/tentsTemplate1.fxml", "/Views/tents/tentsTemplate2.fxml"};
        int rand = randomGenerator(games.length) - 1;
        Parent page = FXMLLoader.load(getClass().getResource(games[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private int randomGenerator(int number) {
        return (int) Math.floor((Math.random() * number + 1));
    }

    public void playTimeAttack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/TimeAttack/sudokuTimeAttack.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playMemory(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/memory.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showTents(MouseEvent mouseEvent) {
        tents1.setVisible(true);
        tents2.setVisible(true);
        tentsText.setVisible(true);
    }

    public void hideTents(MouseEvent mouseEvent) {
        tents1.setVisible(false);
        tents2.setVisible(false);
        tentsText.setVisible(false);
    }

    public void showTimeAttack(MouseEvent mouseEvent) {
        timeAttack1.setVisible(true);
        timeAttack2.setVisible(true);
        timeAttackText.setVisible(true);
    }

    public void hideTimeAttack(MouseEvent mouseEvent) {
        timeAttack1.setVisible(false);
        timeAttack2.setVisible(false);
        timeAttackText.setVisible(false);
    }

    public void showMemory(MouseEvent mouseEvent) {
        memory2.setVisible(true);
        memory1.setVisible(true);
        memoryText.setVisible(true);
    }

    public void hideMemory(MouseEvent mouseEvent) {
        memory1.setVisible(false);
        memory2.setVisible(false);
        memoryText.setVisible(false);
    }
}