package controllers;

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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class SudokuGame implements Initializable {
    // Main AnchorPane
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Pane difficultyPane;
    @FXML
    private Text numHints;
    @FXML
    private Button hintButton;
    @FXML
    private Text gameTime;
    private GameTimer timer;
    // Sudoku Board
    private Label[][] mat = new Label[9][9];
    private Label[][] key = new Label[9][9];
    private int difficulty;
    private int hints;

    // Sets up the board
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws RuntimeException {
        difficultyPane.setVisible(true);

        // Initializes all labels into a 2d array
        int row = 0;
        int column = -1;
        int count = 1;
        for (Node node : anchorpane.getChildren()) {
            if (node instanceof Label) {
                column++;
                mat[row][column] = new Label();
                mat[row][column] = (Label) node;
                mat[row][column].setFocusTraversable(true);
                if (column != 0 && column % 8 == 0) {
                    column = -1;
                    row++;
                }
            }
        }
        timer = new GameTimer(gameTime);
        timer.start();
    }

    /* Changes number by 1 */
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

    /* Randomly generated number between 1 and number */
    private int randomGenerator(int number) {
        return (int) Math.floor((Math.random() * number + 1));
    }

    /* Fills a 3x3 grid with logical numbers */
    public boolean fillBoxUsingStack(int row, int column) {
        int r = 0;
        int c = 0;
        int count = 0;
        Stack<Integer> stack = fillStack();
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            if (checkRow(row + r, pop) && checkCol(column + c, pop) && mat[row + r][column + c].getText().isEmpty()) {
                mat[row + r][column + c].setText(pop + "");
                mat[row + r][column + c].setFocusTraversable(true);
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

    /* Checks 3x3 grid if no repeating numbers */
    public boolean checkGrid(int row, int column, int num) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (mat[row + i][column + j].getText().equals(num + "")) {
                    return false;
                }
        return true;
    }

    /* Provides a stack with randomly shuffled numbers 1-9 */
    public Stack<Integer> fillStack() {
        Stack<Integer> stack = new Stack<>();
        while (stack.size() < 9) {
            int rand = randomGenerator(9);
            if (stack.search(rand) == -1)
                stack.push(rand);
        }
        return stack;
    }

    /* Checks whole row to see of number repeats itself */
    public boolean checkRow(int i, int number) {
        for (int j = 0; j < 9; j++)
            if (mat[i][j].getText().equals(number + "")) {
                return false;
            }
        return true;
    }

    /* Checks column if number repeats itself */
    public boolean checkCol(int j, int number) {
        for (int i = 0; i < 9; i++)
            if (mat[i][j].getText().equals(number + ""))
                return false;
        return true;
    }

    /* Wipes out entire sudoku board */
    public void wipeAll() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                mat[i][j].setText("");
    }

    /* Checks if solved */
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!mat[i][j].getText().equals(key[i][j].getText()))
                    return false;
            }
        }
        return true;
    }

    /* Check if sudoku board is full */
    public boolean isMatrixFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mat[i][j].getText().isEmpty())
                    return false;
            }
        }
        return true;
    }

    /* Checks if 3x3 grid is full */
    public boolean gridIsFull(int row, int column) {
        int rowi = row;
        int coli = column;
        for (int i = 0; i < 9; i++) {
            if (coli == (column + 3)) {
                coli = column;
                rowi++;
            }
            if (mat[rowi][coli].getText().isEmpty())
                return false;
            coli++;
        }
        return true;
    }

    /*  Each box has a chance of being removed depending on number represents difficulty
     *  1 - Easy
     *  2 - Medium
     *  3 - Hard
     *  The Harder the difficulty, the higher chance a number gets removed
     */
    public void remove(int number) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int rand = randomGenerator(10 * number);
                if (rand >= 8) {
                    mat[i][j].setText("");
                    mat[i][j].setFocusTraversable(false);
                }
            }
        }
    }

    public void goBack(ActionEvent event) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /* Resets the board */
    public void resetBoard(ActionEvent actionEvent) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (mat[i][j].focusTraversableProperty().getValue() == false)
                    mat[i][j].setText("");
        }
    }

    /* Gives hint, depending on difficulty */
    public void giveHint(ActionEvent actionEvent) {
        System.out.println(isSolved());
        if (difficulty <= 3) {
            for (int i = 0; i < 50; i++) {
                int r1 = randomGenerator(9) - 1;
                int r2 = randomGenerator(9) - 1;
                String s2 = key[r1][r2].getText();
                if (mat[r1][r2].focusTraversableProperty().getValue() == false) {
                    mat[r1][r2].setText(s2);
                    mat[r1][r2].setTextFill(Color.RED);
                    mat[r1][r2].setFocusTraversable(true);
                    hints--;
                    numHints.setText("Hints: " + hints);
                    break;
                }
            }
            difficulty++;
        }
    }

    public void setEasy(ActionEvent actionEvent) {
        hintButton.setDisable(false);
        difficulty = 1;
        hints = 3;
        numHints.setText("Hints: " + hints);
        difficultyPane.setVisible(false);
        if (!difficultyPane.isVisible()) {
            // Fills out sudoku board
            for (int row = 0; row <= 6; row += 3) {
                for (int column = 0; column <= 6; column += 3) {
                    if (fillBoxUsingStack(row, column)) {
                        row = 0;
                        column = -3;
                    }
                }
            }
            // Copies board full of answers to key array
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String s = mat[i][j].getText();
                    key[i][j] = new Label(s);
                }
            }

            // Removes numbers
            remove(difficulty);
        }
    }

    public void setMedium(ActionEvent actionEvent) {
        hintButton.setDisable(false);
        difficulty = 2;
        hints = 2;
        numHints.setText("Hints: " + hints);
        difficultyPane.setVisible(false);
        if (!difficultyPane.isVisible()) {
            // Fills out sudoku board
            for (int row = 0; row <= 6; row += 3) {
                for (int column = 0; column <= 6; column += 3) {
                    if (fillBoxUsingStack(row, column)) {
                        row = 0;
                        column = -3;
                    }
                }
            }

            // Copies board full of answers to key array
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String s = mat[i][j].getText();
                    key[i][j] = new Label(s);
                }
            }

            // Removes numbers
            remove(difficulty);
        }
    }

    public void setHard(ActionEvent actionEvent) {
        hintButton.setDisable(false);
        difficulty = 3;
        hints = 1;
        numHints.setText("Hints: " + hints);
        difficultyPane.setVisible(false);
        if (!difficultyPane.isVisible()) {
            // Fills out sudoku board
            for (int row = 0; row <= 6; row += 3) {
                for (int column = 0; column <= 6; column += 3) {
                    if (fillBoxUsingStack(row, column)) {
                        row = 0;
                        column = -3;
                    }
                }
            }

            // Copies board full of answers to key array
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String s = mat[i][j].getText();
                    key[i][j] = new Label(s);
                }
            }

            // Removes numbers
            remove(difficulty);
        }
    }
}