package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSelectController implements Initializable {

    @FXML
    ImageView sudokuImage, sudokuImage2, hangmanImage, hangmanImage2, shyguy1, shyguy2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /*Sudoku Methods*/
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
    }

    public void hideImage(MouseEvent mouseEvent) {
        sudokuImage.setVisible(false);
        sudokuImage2.setVisible(false);
    }

    /*Hangman methods*/
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
    }

    public void hideHangManImage(MouseEvent mouseEvent) {
        hangmanImage.setVisible(false);
        hangmanImage2.setVisible(false);
    }

    /*Jigsaw Methods*/
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
    }

    public void hideJigsaw(MouseEvent mouseEvent) {
        shyguy1.setVisible(false);
        shyguy2.setVisible(false);
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

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    public void playTimeAttack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/TimeAttack/sudokuTimeAttack.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
