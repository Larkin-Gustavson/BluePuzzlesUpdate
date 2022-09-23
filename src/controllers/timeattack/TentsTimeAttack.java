package controllers.timeattack;

import controllers.GameSelectController;
import controllers.LoginController;
import db.TimeAttackLeaderboard;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import misc.LimitTimer;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Stack;

public class TentsTimeAttack implements Initializable {
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane winScreen;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane loseScreen;
    @FXML
    Label gameTime;
    private LimitTimer timer;
    @FXML
    private Text points;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Stack<Pane> stack = new Stack<>();
        /*Makes every box except the trees empty*/
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane && !(node.isDisable())) {
                ImageView imageView = (ImageView) ((Pane) node).getChildren().get(0);
                imageView.setVisible(false);
                if (node.focusTraversableProperty().getValue() == true)
                    stack.push((Pane) node);
            }
        }

        stack.pop();
        Collections.shuffle(stack);
        System.out.println(stack);
        for (Pane p : stack) {
            p.getChildren().get(0).setVisible(true);
        }
        // Game Timer
        timer = new LimitTimer(gameTime);
        timer.start();
        gameTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                if (newValue.equals("0"))
                    loseScreen.setVisible(true);

            }
        });
        points.setText("Points: " + GameSelectController.totalPoints);

    }

    /*Box clicked then give it a tent*/
    public void giveTent(MouseEvent mouseEvent) {
        Pane box = (Pane) mouseEvent.getSource();
        ImageView imageView = (ImageView) box.getChildren().get(0);
        // Makes tent visible and invisible when clicked
        if (imageView.isVisible())
            imageView.setVisible(false);
        else imageView.setVisible(true);

    }

    /*Reset the board*/
    public void reset(ActionEvent actionEvent) {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Pane) {
                ImageView imageView = (ImageView) ((Pane) node).getChildren().get(0);
                if (node.getId() == null)
                    imageView.setVisible(false);
            }
        }
    }

    /*Check to see if won*/
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
        GameSelectController.totalPoints++;
        showWinScreen(true);
        return true;

    }

    public void showWinScreen(boolean win) {
        winScreen.setVisible(true); // set win screen visible
        timer.stop(); // stop game timer
        gameTime.setDisable(true);
        for (Node node : anchorPane.getChildren())
            if (node instanceof Button)
                node.setDisable(true);
    }


    public void goBack(ActionEvent actionEvent) throws Exception {
        TimeAttackLeaderboard.insertNewUser(LoginController.user, GameSelectController.totalPoints);
        GameSelectController.totalPoints = 0;
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        timer.stop();
    }

    public void playAgain(ActionEvent actionEvent) throws Exception {
        // Pick a game at random
        String[] easyGames = {"/Views/tents/tentsTemplate1.fxml"};
        int rand = randomGenerator(1) - 1;
        // Load the game
        Parent page = FXMLLoader.load(getClass().getResource(easyGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    int randomGenerator(int number) {
        return (int) Math.floor((Math.random() * number + 1));
    }

    public void goNext(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/TimeAttack/SudokuTimeAttack.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
