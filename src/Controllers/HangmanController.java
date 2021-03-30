package Controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Stack;

public class HangmanController implements Initializable {


    String[] words = {"School", "Laundry", "House", "Gameboy", "Amazing", "Educational", "Puzzle"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int pick = randomGenerator(words.length - 1);
        String word = words[pick];
    }


    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }


}



