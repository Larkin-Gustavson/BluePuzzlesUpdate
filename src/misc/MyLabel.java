package misc;

import javafx.scene.control.Label;

public class MyLabel extends Label {
    private char letter;

    public MyLabel(char letter) {
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
