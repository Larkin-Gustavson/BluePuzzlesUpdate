package Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Stack;

public class HangmanController implements Initializable {

    @FXML
    AnchorPane hangmanAnchorPane;

    String[] words = {"School", "Laundry", "House", "Gameboy", "Amazing", "Educational", "Puzzle"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Select a rand word from the array*/
        int pick = randomGenerator(words.length - 1);
        String word = words[pick]; //the word

        int pos = 0;
        for (int i = 0; i < word.length(); i++) {
            myLabel label = new myLabel(word.charAt(i));
            label.setFont(new Font("System", 30));
            hangmanAnchorPane.getChildren().add(label);
            label.relocate(200 + pos, 200);
            pos += 45;
        }

    }


    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }


    public void doSomething(MouseEvent mouseEvent) {
        Text source = (Text) mouseEvent.getSource();
        System.out.println(source.getText());
        source.setDisable(true);
        Paint p = Color.GREY;
        source.setFill(p);
    }
}



