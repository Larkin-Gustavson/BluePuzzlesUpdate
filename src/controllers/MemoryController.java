package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import misc.GameTimer;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Stack;

public class MemoryController implements Initializable {
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane winScreen;
    @FXML
    private AnchorPane difficultySelect;
    @FXML
    private Text gameTime;
    @FXML
    private Text finishTime;
    @FXML
    private Text freeFlips;
    private GameTimer timer;
    private Pane firstCard;
    private static String difficulty = "";
    private int numberOfFreeFlips = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new GameTimer(gameTime);
        timer.start();
        freeFlips.setText("Free Flips: " + numberOfFreeFlips);
        randomize();
    }

    public void randomize() {
        Stack<Pair<Double, Double>> layouts = new Stack<>();

        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane && !(node instanceof AnchorPane)) {
                Pair<Double, Double> layout = new Pair<>(node.getLayoutX(), node.getLayoutY());
                layouts.push(layout);
            }
        }

        Collections.shuffle(layouts);

        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane && !(node instanceof AnchorPane)) {
                Pair<Double, Double> newLayout = layouts.pop();

                node.setLayoutX(newLayout.getKey());
                node.setLayoutY(newLayout.getValue());
            }
        }
    }

    public void flipCard(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        for (Node node : gamePane.getChildren()) { // Unflips any unmatched card
            if (node instanceof Pane && !(node instanceof AnchorPane))
                if (node.focusTraversableProperty().getValue() == false && firstCard == null) {
                    Pane p = (Pane) node;
                    p.getChildren().get(1).setVisible(true);
                }
        }

        // Flip a card
        if (pane.getChildren().get(1).isVisible() == true && pane.focusTraversableProperty().getValue() == false) {
            pane.getChildren().get(1).setVisible(false);
        } else if (pane.getChildren().get(1).isVisible() == false && pane.focusTraversableProperty().getValue() == false) {
            pane.getChildren().get(1).setVisible(true);
        }

        // If card is first card pulled
        if (firstCard == null && pane.focusTraversableProperty().getValue() == false
            && pane.getChildren().get(1).isVisible() == false)
            firstCard = pane;
        else if (firstCard != null && pane.focusTraversableProperty().getValue() == false
                 && pane.getChildren().get(1).isVisible() == false) {
            ImageView fc = (ImageView) firstCard.getChildren().get(0);
            ImageView sc = (ImageView) pane.getChildren().get(0);
            if (fc.getImage().getUrl().equals(sc.getImage().getUrl())) { // IF MATCH
                firstCard.setFocusTraversable(true);
                pane.setFocusTraversable(true);
            } else {
                if (numberOfFreeFlips > 0)
                    numberOfFreeFlips--;
                freeFlips.setText("Free Flips: " + numberOfFreeFlips);
                if (numberOfFreeFlips == 0)
                    timer.addTen();
            }
            firstCard = null;
        }

        // If final pair is matched
        if (isWon()) {
            showWinScreen();
            try {
                Leaderboard.insertNewUser("MemoryLeaderboard", LoginController.getUser(), gameTime.getText(),
                        difficulty);
            } catch (SQLException e) {
                System.out.printf("ERROR");
            }
        }
    }

    // Checks if game is won
    public boolean isWon() {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane && !(node instanceof AnchorPane))
                if (((Pane) node).getChildren().get(1).isVisible())
                    return false;
        }
        return true;
    }

    // Show Win Screen
    public void showWinScreen() {
        winScreen.setVisible(true);
        timer.stop();
        finishTime.setText("Your Time: " + timer.toString());
    }

    // Back Button
    public void goBack(ActionEvent event) throws Exception {
        timer.stop();
        Parent page = FXMLLoader.load(getClass().getResource("/views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Set the Difficulty - easy, medium, hard
    public void setDifficulty(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        difficulty += button.getText(); // Set the difficulty
        difficultySelect.setVisible(false); // Hide pane

        if (difficulty.equals("easy"))
            numberOfFreeFlips = 5;
        else if (difficulty.equals("medium"))
            numberOfFreeFlips = 3;
        else
            numberOfFreeFlips = 1;

        freeFlips.setText("Free Flips: " + numberOfFreeFlips);
    }
}