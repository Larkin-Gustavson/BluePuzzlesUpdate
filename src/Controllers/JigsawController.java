package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class JigsawController implements Initializable {

    @FXML
    ImageView check1;
    @FXML
    Pane gamePane, pane1;
    @FXML
    AnchorPane anchorpane;
    @FXML
    Button button;


    Pane selected; //Current selected pane
    Stack<Pair> original = new Stack<>(); //Winning positions

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Scramble pics
        Stack<Pane> stack = new Stack<>();

        for (Node node : anchorpane.getChildren())
            if (node instanceof Pane) {
                stack.push((Pane) node);
                Pair<Double, Double> coords = new Pair<Double, Double>(node.getLayoutX(), node.getLayoutY());
                original.push(coords);
            }

        Collections.shuffle(stack);
        int rand = randomGenerator(3);

        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Pane) {
                node.setRotate(node.getRotate() + (90 * rand));
                swapPanes(stack.pop(), (Pane) node);
            }
        }

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

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    public void checkWin(ActionEvent actionEvent) {
        Stack<Pair> stack = new Stack<>();
        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Pane) {
                Pair<Double, Double> coords = new Pair<Double, Double>(node.getLayoutX(), node.getLayoutY());
                stack.push(coords);
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
        System.out.println("You Win!");
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/jigsawDifficulty.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
