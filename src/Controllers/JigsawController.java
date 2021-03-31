package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Stack;

public class JigsawController implements Initializable {

    @FXML
    ImageView check1;
    @FXML
    Pane gamePane, pane1;
    @FXML
    AnchorPane anchorpane;
    @FXML
    Button button;

    Pane selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Scramble pics
        Stack<Pane> stack = new Stack<>();
        for (Node node : anchorpane.getChildren())
            if (node instanceof Pane)
                stack.push((Pane) node);

        Collections.shuffle(stack);
        int rand = randomGenerator(3);

        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Pane) {
                node.setRotate(node.getRotate() + (90 * rand));
                swapPanes(stack.pop(), (Pane) node);
            }
        }

    }


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

    public boolean isSelected() {
        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Pane)
                if (node.focusTraversableProperty().getValue() == true)
                    return true;
        }
        return false;
    }

    public void rotate(KeyEvent keyEvent) {
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


}
