package Controllers.TimeAttack;

import Controllers.GameSelectController;
import Controllers.HangmanDifficultyController;
import Controllers.LoginController;
import DB.Leaderboard;
import DB.TimeAttackLeaderboard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import misc.limitTimer;
import misc.myLabel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HangmanTimeAttack implements Initializable {

    @FXML
    AnchorPane hangmanAnchorPane;
    @FXML
    Pane hangmanBody, guessWordPane, wordPane, winScreen, loseScreen;
    @FXML
    TextField guessWordField;
    @FXML
    Text livesText, finishTime, points, highScore;
    @FXML
    private Button hintButton;
    @FXML
    Label gameTime;


    int lives = 1;
    String[] words = {"School", "Laundry", "House", "Gameboy Advanced", "Amazing", "Educational", "Puzzle",
            "Blue Puzzles", "Smoke", "Maple Syrup"};
    String word;
    limitTimer gt; //Timer

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            highScore.setText("High Score: " + TimeAttackLeaderboard.getHighScore(LoginController.user));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        /*Select a rand word from the array*/
        int pick = randomGenerator(words.length - 1);
        word = words[pick].toUpperCase(); //the word
        System.out.println(word);

        /*Positions the word*/
        int pos = 0;

        for (int i = 0; i < word.length(); i++) {
            int rand = randomGenerator(2);
            myLabel label = new myLabel(word.charAt(i));
            label.setFont(new Font("System", 30));
            wordPane.getChildren().add(label);
            if (rand == 1) label.showLetter(); //Shows letter
            label.relocate(10 + pos, 5);
            pos += 40;
        }
        points.setText("Points: " + GameSelectController.totalpoints);
        gt = new limitTimer(gameTime);
        gt.start();
        gameTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                if (newValue.equals("0"))
                    loseScreen.setVisible(true);

            }
        });

    }


    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }


    public void doSomething(MouseEvent mouseEvent) {
        /*Changes to letter clicked*/
        Text source = (Text) mouseEvent.getSource();
        System.out.println(source.getText());
        source.setDisable(true);
        Paint p = Color.GREY;
        source.setFill(p);
        char guess = source.getText().charAt(0); //guessed letter
        /*Changes to word*/
        boolean guessRight = false;
        for (Node node : wordPane.getChildren()) {
            if (node instanceof myLabel) {
                myLabel label = (myLabel) node;
                if (guess == label.getLetter()) {
                    label.showLetter();
                    guessRight = true;
                    gameWon();
                }
            }
        }
        if (!guessRight)
            wrongGuess();
    } //End of Method

    /*Determines if the game is won*/
    public boolean gameWon() {
        for (Node node : wordPane.getChildren()) {
            if (node instanceof myLabel) {
                if (((myLabel) node).getText().equals("_"))
                    return false;
            }
        }
        showWinScreen(true);
        System.out.println("You won!");
        return true;
    }

    public void guessWord(ActionEvent actionEvent) {
        guessWordPane.setVisible(true);
    }

    public void enterGuess(ActionEvent actionEvent) {
        guessWordPane.setVisible(false);
        if (guessWordField.getText().toUpperCase().equals(word)) {
            System.out.println("You win!");
            showWinScreen(true);
            for (Node node : wordPane.getChildren()) {
                if (node instanceof myLabel) {
                    ((myLabel) node).showLetter();
                }
            }
        } else {
            wrongGuess();
            guessWordField.clear();
        }

    }

    public void showWinScreen(boolean win) {
        GameSelectController.totalpoints++;
        winScreen.setVisible(true);
        gt.stop();

        for (Node node : hangmanAnchorPane.getChildren())
            if (node instanceof Button)
                node.setDisable(true);
    }

    public void wrongGuess() {
        lives--;
        livesText.setText(lives + "");
        if (lives == 0)
            loseScreen.setVisible(true);
        for (Node body : hangmanBody.getChildren()) {
            if (!body.isVisible()) {
                body.setVisible(true);
                break;
            }
        }
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        TimeAttackLeaderboard.insertNewUser(LoginController.user, GameSelectController.totalpoints);
        GameSelectController.totalpoints = 0;
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playAgain(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangman.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void goNext(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/TimeAttack/jigsaw1TimeAttack.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}



