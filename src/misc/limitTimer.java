package misc;

import Controllers.TimeAttack.SudokuTimeAttack;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class limitTimer extends TimerTask {

    Timer timer;
    Label gameTime; // Label that changes
    Text gameTimeText;
    int timePassed = 11; //Actual seconds
    int seconds = timePassed % 60; //Calculated seconds 0-60
    String secondsString = String.format("%02d", seconds);// Double Digit format
    int minutes = timePassed / 60; //Calculated minutes
    String minutesString = String.format("%02d", seconds); // Double Digit format


    public limitTimer(Label time) {
        gameTime = time;
    }

    public limitTimer(Text time) {
        gameTimeText = time;
    }

    /*Run function that changes label text with the time*/
    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            public void run() {
                timePassed--;
                if (gameTime != null)
                    gameTime.setText(timePassed + "");
                else if (gameTimeText != null)
                    gameTimeText.setText(timePassed + "");
                if (timePassed == 0) {
                    stop();
                }
            }
        });
    }

    /*Performs the task*/
    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 1000, 1000);

    }

    public String toString() {
        return minutesString + ":" + secondsString;
    }

    public void stop() {
        timer.cancel();
        timer.purge();
        this.cancel();

    }

    public int giveTime() {
        return timePassed;
    }


}
