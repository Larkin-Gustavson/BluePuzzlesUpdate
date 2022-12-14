package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import misc.GameTimer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TentsController implements Initializable {
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane winScreen;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label gameTime;
    private GameTimer timer;
    @FXML
    private Text winOrLose;
    @FXML
    private Text finishTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Makes every box except the trees empty */
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane && !(node.isDisable())) {
                ImageView imageView = (ImageView) ((Pane) node).getChildren().get(0);
                imageView.setVisible(false);
            }
        }
        // Game Timer
        timer = new GameTimer(gameTime);
        timer.start();

    }

    /* Box clicked then give it a tent */
    public void giveTent(MouseEvent mouseEvent) {
        Pane box = (Pane) mouseEvent.getSource();
        ImageView imageView = (ImageView) box.getChildren().get(0);
        // Makes tent visible and invisible when clicked
        if (imageView.isVisible())
            imageView.setVisible(false);
        else imageView.setVisible(true);

    }

    /* Reset the board */
    public void reset(ActionEvent actionEvent) {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane) {
                ImageView imageView = (ImageView) ((Pane) node).getChildren().get(0);
                if (node.getId() == null)
                    imageView.setVisible(false);
            }
        }
    }

    /* Check to see if won */
    public boolean check(ActionEvent actionEvent) throws SQLException {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane) {
                ImageView imageView = (ImageView) ((Pane) node).getChildren().get(0);

                // Checks if any box should not have a tent
                if (imageView.isVisible() && node.focusTraversableProperty().getValue() == false && node.getId() == null)
                    return false;
                // Checks if a box should have a tent
                if (imageView.isVisible() == false && node.focusTraversableProperty().getValue() == true && node.getId() == null)
                    return false;


            }
        }
        showWinScreen(true);
        return true;
    }

    public void showWinScreen(boolean win) throws SQLException {
        if (win)
            winOrLose.setText("You Won!");
        else
            winOrLose.setText("You lost!");
        winScreen.setVisible(true); // set win screen visible
        finishTime.setText(gameTime.getText()); // reveal finishing game time
        Leaderboard.insertNewUser("TentsLeaderboard", LoginController.getUser(), gameTime.getText(),
                "regular");
        timer.stop(); // stop game timer
        gameTime.setDisable(true);
        for (Node node : anchorPane.getChildren())
            if (node instanceof Button)
                node.setDisable(true);
    }


    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        timer.stop();
    }

    public void playAgain(ActionEvent actionEvent) throws Exception {
        // Pick a game at random
        String[] easyGames = {"/views/tents/tentsTemplate1.fxml"};
        int rand = randomGenerator(1) - 1;
        // Load the game
        Parent page = FXMLLoader.load(getClass().getResource(easyGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private int randomGenerator(int number) {
        return (int) Math.floor((Math.random() * number + 1));
    }
}
