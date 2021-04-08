package misc;

import javafx.scene.control.Label;

public class myLabel extends Label {

    private char letter;

    public myLabel(char letter) {
        if (letter != ' ')
            this.setText("_");
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public void showLetter() {
        this.setText(letter + "");
    }


}
