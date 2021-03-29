package Controllers;

import javafx.scene.control.Label;

public class myLabel extends Label {

    char letter;

    public myLabel(char letter){
        this.setText("_");
        this.letter = letter;
    }

}
