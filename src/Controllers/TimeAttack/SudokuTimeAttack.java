package Controllers.TimeAttack;

import Controllers.GameSelectController;
import Controllers.LoginController;
import DB.TimeAttackLeaderboard;
import com.mysql.cj.log.Log;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import misc.GameTimer;
import misc.limitTimer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;


public class SudokuTimeAttack implements Initializable {

    //Main AnchorPane
    @FXML
    AnchorPane anchorpane;
    @FXML
    Pane difficultyPane, winScreen, loseScreen;
    @FXML
    Text gameTime, points, highScore;


    //Sudoku Board
    private Label mat[][] = new Label[9][9];
    private Label[][] key = new Label[9][9];
    private int difficulty;
    static int totalPoints = 0;
    limitTimer gt;

    //Sets up the board
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws RuntimeException {
        difficultyPane.setVisible(true);
        try {
            highScore.setText("High Score: " + TimeAttackLeaderboard.getHighScore(LoginController.user));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Initializes all labels into a 2d array
        int row = 0, col = -1;
        int count = 1;
        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Label) {
                col++;
                mat[row][col] = new Label();
                mat[row][col] = (Label) node;
                mat[row][col].setFocusTraversable(true);
                if (col != 0 && col % 8 == 0) {
                    col = -1;
                    row++;
                }

            }
        }

    }

    /*Changes number by 1*/
    public void changeNumber(MouseEvent mouseEvent) {
        Label source = (Label) mouseEvent.getSource();

        if (source.getText().isEmpty() && source.focusTraversableProperty().getValue() == false) {
            source.setText(1 + "");
            source.setTextFill(Color.DODGERBLUE);
        } else {
            int num = Integer.parseInt(source.getText());
            if (num == 9) num = 1;
            else num++;
            if (source.focusTraversableProperty().getValue() == false) {
                source.setText(num + "");
                source.setTextFill(Color.DODGERBLUE);
            }

        }

    }

    /*randomly generated number between 1 and num*/
    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    /*Fills a 3x3 grid with logical numbers*/
    public boolean fillBoxUsingStack(int row, int col) {
        int r = 0, c = 0;
        int count = 0;
        Stack<Integer> stack = fillStack();
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            if (checkRow(row + r, pop) && checkCol(col + c, pop) && mat[row + r][col + c].getText().isEmpty()) {
                mat[row + r][col + c].setText(pop + "");
                mat[row + r][col + c].setFocusTraversable(true);
            } else {
                count++;
                stack.push(pop);
                c++;
                if (count > 50) {
                    wipeAll();
                    stack = fillStack();
                    c = 0;
                    r = 0;
                    count = 0;
                    return true;
                }
            }
            if (c == 3) {
                c = 0;
                r++;
                if (r == 3)
                    r = 0;
            }
        }
        return false;
    }

    /*Checks 3x3 grid if no repeating numbers*/
    public boolean checkgrid(int row, int col, int num) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (mat[row + i][col + j].getText().equals(num + "")) {
                    return false;
                }
        return true;
    }

    /*Provides a stack with randomly shuffled numbers 1-9*/
    public Stack<Integer> fillStack() {
        Stack<Integer> stack = new Stack<Integer>();
        while (stack.size() < 9) {
            int rand = randomGenerator(9);
            if (stack.search(rand) == -1)
                stack.push(rand);
        }
        return stack;
    }

    /*Checks whole row to see of num repeats itself*/
    public boolean checkRow(int i, int num) {
        for (int j = 0; j < 9; j++)
            if (mat[i][j].getText().equals(num + "")) {
                return false;
            }
        return true;
    }

    /*Checks column if num repeats itself*/
    public boolean checkCol(int j, int num) {
        for (int i = 0; i < 9; i++)
            if (mat[i][j].getText().equals(num + ""))
                return false;
        return true;
    }

    /*Wipes out entire sudoku board*/
    public void wipeAll() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                mat[i][j].setText("");
    }

    /*Checks if solved*/
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!mat[i][j].getText().equals(key[i][j].getText()))
                    return false;
            }
        }
        return true;
    }

    /*Check if sudoku board is full*/
    public boolean matisFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mat[i][j].getText().isEmpty())
                    return false;
            }
        }
        return true;
    }

    /*Checks if 3x3 grid is full*/
    public boolean gridIsFull(int row, int col) {
        int rowi = row, coli = col;
        for (int i = 0; i < 9; i++) {
            if (coli == (col + 3)) {
                coli = col;
                rowi++;
            }
            if (mat[rowi][coli].getText().isEmpty())
                return false;
            coli++;
        }
        return true;

    }

    /*Each box has a chance of being removed depending on num
     * num represents difficulty
     * 1 - Easy
     * 2 - Medium
     * 3 - Hard
     * The Harder the difficulty, the higher chance a number gets removed
     */
    public void remove(int num) {
        int rand = randomGenerator(81);
        int rand2 = randomGenerator(81);
        mat[rand % 9][rand2 % 9].setText("");
        mat[rand % 9][rand2 % 9].setFocusTraversable(false);
    }

    public void goBack(ActionEvent event) throws Exception {
        TimeAttackLeaderboard.insertNewUser(LoginController.user, GameSelectController.totalpoints);
        GameSelectController.totalpoints = 0;
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void setEasy(ActionEvent actionEvent) {
        points.setText("Points: " + GameSelectController.totalpoints);
        gt = new limitTimer(gameTime);
        gt.start();
        gameTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                if (newValue.equals("0"))
                    loseScreen.setVisible(true);

            }
        });
        difficulty = 1;
        difficultyPane.setVisible(false);
        if (!difficultyPane.isVisible()) {
            //Fills out sudoku board
            for (int r = 0; r <= 6; r += 3) {
                for (int c = 0; c <= 6; c += 3) {
                    if (fillBoxUsingStack(r, c)) {
                        r = 0;
                        c = -3;
                    }
                }
            }
            //Copies board full of answers to key array
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String s = mat[i][j].getText();
                    key[i][j] = new Label(s);
                }
            }

            //Removes numbers
            remove(difficulty);
        }
    }


    public void checkWin(ActionEvent actionEvent) {
        if (isSolved()) {
            winScreen.setVisible(true);
            gt.stop();
            GameSelectController.totalpoints++;

        }
    }

    public void gameOver() {

    }

    public void goNext(ActionEvent event) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/TimeAttack/hangmanTimeAttack.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
