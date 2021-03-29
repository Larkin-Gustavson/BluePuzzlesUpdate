package Controllers;

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


import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ResourceBundle;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class SudokuGame implements Initializable {


    //    @FXML
    //    Label box,box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,box12,box13,box14,box15,box16,
    //    box17,box18,box19,box20,box21,box22,box23,box24,box25,box26,box27,box28,box29,box30,box31,box32,
    //    box33,box34,box35,box37,box38,box39,box40,box41,box42,box43,box44,box45,box46,box47,box48,
    //    box49,box50,box51,box52,box53,box54,box55,box56,box57,box58,box59,box60,box61,box62,box63,box64,
    //    box65,box66,box67,box68,box69,box70,box71,box72,box73,box74,box75,box76,box77,box78,box79,box80,box81;

    //Main AnchorPane
    @FXML
    AnchorPane anchorpane;
    @FXML
    Pane difficultyPane;
    @FXML
    Text numHints;
    @FXML
    Button hintButn;

    //Sudoku Board
    private Label mat[][] = new Label[9][9];
    private Label[][] key = new Label[9][9];
    private int difficulty;
    private int hints;

    //Sets up the board
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws RuntimeException {
        difficultyPane.setVisible(true);

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
    public boolean fillBoxUsingStack(int row, int col){
        int r=0,c=0;
        int count = 0;
        Stack<Integer> stack = fillStack();
        while(!stack.isEmpty()){
            int pop = stack.pop();
            if(checkRow(row+r,pop) && checkCol(col+c,pop) && mat[row+r][col+c].getText().isEmpty()) {
                mat[row + r][col + c].setText(pop + "");
                mat[row + r][col + c].setFocusTraversable(true);
            }
            else {
                count++;
                stack.push(pop);
                c++;
                if(count > 50){
                    wipeAll();
                    stack = fillStack();
                    c= 0;
                    r=0;
                    count=0;
                    return true;
                }
            }
            if(c==3){
                c=0;
                r++;
                if(r==3)
                    r=0;
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
    public Stack<Integer> fillStack(){
        Stack<Integer> stack = new Stack<Integer>();
        while(stack.size()<9){
            int rand = randomGenerator(9);
            if(stack.search(rand)==-1)
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
    public void wipeAll(){
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                mat[i][j].setText("");
    }

    /*Checks if solved*/
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!mat[i][j].getText().equals(key[i][j].getText()))
                    return false;
            }
        }
        return true;
    }

    /*Check if sudoku board is full*/
    public boolean matisFull(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(mat[i][j].getText().isEmpty())
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int rand = randomGenerator(10*num);
                if(rand >=8) {
                    mat[i][j].setText("");
                    mat[i][j].setFocusTraversable(false);
                }
            }
        }
    }

    public void goBack(ActionEvent event) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/mainmenu.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*Resets the board*/
    public void resetBoard(ActionEvent actionEvent) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if(mat[i][j].focusTraversableProperty().getValue()==false)
                    mat[i][j].setText("");
        }
    }

    /*Gives hint, depending on difficulty*/
    public void giveHint(ActionEvent actionEvent) {
        System.out.println(isSolved());
        if(difficulty<=3) {
            for (int i = 0; i < 50; i++) {
                int r1 = randomGenerator(9) - 1;
                int r2 = randomGenerator(9) - 1;
                String s2 = key[r1][r2].getText();
                if (mat[r1][r2].focusTraversableProperty().getValue()==false) {
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
        hintButn.setDisable(false);
        difficulty =1;
        hints=3;
        numHints.setText("Hints: " + hints);
        difficultyPane.setVisible(false);
        if(!difficultyPane.isVisible()) {
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

    public void setMedium(ActionEvent actionEvent) {
        hintButn.setDisable(false);
        difficulty =2;
        hints=2;
        numHints.setText("Hints: " + hints);
        difficultyPane.setVisible(false);
        if(!difficultyPane.isVisible()) {
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

    public void setHard(ActionEvent actionEvent) {
        hintButn.setDisable(false);
        difficulty =3;
        hints =1;
        numHints.setText("Hints: " + hints);
        difficultyPane.setVisible(false);
        if(!difficultyPane.isVisible()) {
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
}
