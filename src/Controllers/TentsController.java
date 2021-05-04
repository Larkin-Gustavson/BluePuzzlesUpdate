package Controllers;

import DB.Leaderboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import misc.GameTimer;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TentsController implements Initializable {

    @FXML
    private AnchorPane gamePane, winScreen, anchorpane;
    @FXML
    Label gameTime;
    private GameTimer gt;
    @FXML
    private Text winorlose, finishTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Makes every box except the trees empty*/
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane && !(node.isDisable())) {
                ImageView iv = (ImageView) ((Pane) node).getChildren().get(0);
                iv.setVisible(false);
            }
        }
        //Game Timer
        gt = new GameTimer(gameTime);
        gt.start();

    }

    /*Box clicked then give it a tent*/
    public void giveTent(MouseEvent mouseEvent) {
        Pane box = (Pane) mouseEvent.getSource();
        ImageView iv = (ImageView) box.getChildren().get(0);
        //Makes tent visible and invisible when clicked
        if (iv.isVisible())
            iv.setVisible(false);
        else iv.setVisible(true);

    }

    /*Reset the board*/
    public void reset(ActionEvent actionEvent) {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane) {
                ImageView iv = (ImageView) ((Pane) node).getChildren().get(0);
                if (node.getId() == null)
                    iv.setVisible(false);
            }
        }
    }

    /*Check to see if won*/
    public boolean check(ActionEvent actionEvent) throws SQLException {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane) {
                ImageView iv = (ImageView) ((Pane) node).getChildren().get(0);

                //Checks if any box should not have a tent
                if (iv.isVisible() && node.focusTraversableProperty().getValue() == false && node.getId() == null)
                    return false;
                //Checks if a box should have a tent
                if (iv.isVisible() == false && node.focusTraversableProperty().getValue() == true && node.getId() == null)
                    return false;


            }
        }
        showWinScreen(true);
        return true;

    }

    public void showWinScreen(boolean win) throws SQLException {
        if (win)
            winorlose.setText("You Won!");
        else
            winorlose.setText("You lost!");
        winScreen.setVisible(true); //set win screen visible
        finishTime.setText(gameTime.getText()); //reveal finshing game time
        Leaderboard.insertNewUser("TentsLeaderboard", LoginController.user, gameTime.getText(),
                "regular");
        gt.stop(); // stop game timer
        gameTime.setDisable(true);
        for (Node node : anchorpane.getChildren())
            if (node instanceof Button)
                node.setDisable(true);
    }


    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        gt.stop();
    }

    public void playAgain(ActionEvent actionEvent) throws Exception {
        //Pick a game at random
        String[] easyGames = {"/Views/tents/tentsTemplate1.fxml"};
        int rand = randomGenerator(1) - 1;
        //Load the game
        Parent page = FXMLLoader.load(getClass().getResource(easyGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }
}
