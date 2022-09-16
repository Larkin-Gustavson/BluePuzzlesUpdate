package Controllers.TimeAttack;

import Controllers.GameSelectController;
import Controllers.LoginController;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import misc.LimitTimer;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Stack;

public class JigsawTimeAttack implements Initializable {

    @FXML
    ImageView check1;
    @FXML
    AnchorPane anchorpane;
    @FXML
    AnchorPane winScreen;
    @FXML
    AnchorPane loseScreen;
    @FXML
    Button button;
    @FXML
    Text finishTime;
    @FXML
    Text bestTime;
    @FXML
    Text points;
    @FXML
    Text highScore;
    @FXML
    Label gameTime;


    Pane selected; //Current selected pane
    Stack<Pair> original = new Stack<>(); //Winning positions
    LimitTimer timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            highScore.setText("High Score: " + TimeAttackLeaderboard.getHighScore(LoginController.user));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        //Scramble pics
        Stack<Pane> stack = new Stack<>();

        for (Node node : anchorpane.getChildren())
            if (node instanceof Pane && !(node instanceof AnchorPane)) {
                stack.push((Pane) node);
                Pair<Double, Double> coordinate = new Pair<>(node.getLayoutX(), node.getLayoutY());
                original.push(coordinate);
            }

        Collections.shuffle(stack);
        int rand = randomGenerator(3);

        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Pane && !(node instanceof AnchorPane)) {
                node.setRotate(node.getRotate() + (90 * rand));
                swapPanes(stack.pop(), (Pane) node);
            }
        }
        points.setText("Points: " + GameSelectController.totalPoints);
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

    }

    //Select a pane
    public void select(MouseEvent mouseEvent) {
        Pane source = (Pane) mouseEvent.getSource();
        if (source.focusTraversableProperty().getValue() == true) {
            source.setFocusTraversable(false);
            source.setStyle("-fx-border-color: black");
        } else if (isSelected() == false) {
            source.setFocusTraversable(true);
            source.setStyle("-fx-border-color: red");
            selected = source;

        } else if (isSelected() == true) {
            swapPanes(source, selected);
        }
    }

    //Checks if any panes are selected, returns false if none
    public boolean isSelected() {
        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Pane)
                if (node.focusTraversableProperty().getValue() == true)
                    return true;
        }
        return false;
    }

    //Rotate if 'r' key is pressed
    public void rotate(KeyEvent keyEvent) throws InterruptedException {
        if (isSelected()) {
            if (keyEvent.getCode() == KeyCode.R)
                selected.setRotate(selected.getRotate() + 90);
        }
    }

    public void swapPanes(Pane pane1, Pane pane2) {
        double tempX = pane1.getLayoutX();
        double tempY = pane1.getLayoutY();
        pane1.setLayoutX(pane2.getLayoutX());
        pane1.setLayoutY(pane2.getLayoutY());
        pane2.setLayoutX(tempX);
        pane2.setLayoutY(tempY);
        pane2.setFocusTraversable(false);
        pane2.setStyle("-fx-border-color: black");
    }

    int randomGenerator(int number) {
        return (int) Math.floor((Math.random() * number + 1));
    }

    /*Show win screen*/
    public void showWinScreen(boolean win) throws SQLException {

        winScreen.setVisible(true); //set win screen visible
        timer.stop(); // stop game timer
        gameTime.setDisable(true);
        for (Node node : anchorpane.getChildren())
            if (node instanceof Button)
                node.setDisable(true);

    }

    public void checkWin(ActionEvent actionEvent) throws SQLException {
        Stack<Pair> stack = new Stack<>();
        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Pane && !(node instanceof AnchorPane)) {
                Pair<Double, Double> coordinate = new Pair<>(node.getLayoutX(), node.getLayoutY());
                stack.push(coordinate);
                if (node.getRotate() % 360 != 0) {
                    System.out.println("Not yet!");
                    return;
                }
            }
        }
        if (!stack.equals(original)) {
            System.out.println("Not yet!!!");
            return;
        }
        GameSelectController.totalPoints++;
        showWinScreen(true);
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        TimeAttackLeaderboard.insertNewUser(LoginController.user, GameSelectController.totalPoints);
        GameSelectController.totalPoints = 0;
        Parent page = FXMLLoader.load(getClass().getResource("/Views/jigsawDifficulty.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        timer.stop();
    }


    public void playAgain(ActionEvent actionEvent) throws Exception {
        //Pick a game at random
        String[] easyGames = {"/Views/jigsaw/medium/jigsawShyGuy.fxml"};
        int rand = randomGenerator(1) - 1;
        //Load the game
        Parent page = FXMLLoader.load(getClass().getResource(easyGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goNext(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/TimeAttack/TentsTimeAttack.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
