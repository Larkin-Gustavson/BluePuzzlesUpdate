package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;




public class SudokuGame implements Initializable {

    //<editor-fold desc="Lable variables and array">
    /*Initializing all labels into array*/
    @FXML
    Label box,box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,box12,box13,box14,box15,box16,
    box17,box18,box19,box20,box21,box22,box23,box24,box25,box26,box27,box28,box29,box30,box31,box32,
    box33,box34,box35,box37,box38,box39,box40,box41,box42,box43,box44,box45,box46,box47,box48,
    box49,box50,box51,box52,box53,box54,box55,box56,box57,box58,box59,box60,box61,box62,box63,box64,
    box65,box66,box67,box68,box69,box70,box71,box72,box73,box74,box75,box76,box77,box78,box79,box80,box81;
    @FXML Label[] grid = {box,box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,box12,box13,box14,box15,box16,
            box17,box18,box19,box20,box21,box22,box23,box24,box25,box26,box27,box28,box29,box30,box31,box32,
            box33,box34,box35,box37,box38,box39,box40,box41,box42,box43,box44,box45,box46,box47,box48,
            box49,box50,box51,box52,box53,box54,box55,box56,box57,box58,box59,box60,box61,box62,box63,box64,
            box65,box66,box67,box68,box69,box70,box71,box72,box73,box74,box75,box76,box77,box78,box79,box80,box81};
    /**/
    //</editor-fold>

    SudokuObject so;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SudokuObject so = new SudokuObject(box,box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,box12,
                box13,box14,box15,box16,box17,box18,box19,box20,box21,box22,box23,box24,box25,box26,box27,box28,box29,box30,
                box31,box32,box33,box34,box35,box37,box38,box39,box40,box41,box42,box43,box44,box45,box46,box47,box48,box49,
                box50,box51,box52,box53,box54,box55,box56,box57,box58,box59,box60,box61,box62,box63,box64,box65,box66,box67,
                box68,box69,box70,box71,box72,box73,box74,box75,box76,box77,box78,box79,box80,box81,30);
        //so.fillValues();


    }

    public void changeNumber(MouseEvent mouseEvent) {

        System.out.println(grid[2].getText());
        Label source = (Label) mouseEvent.getSource();
        if (source.getText().isEmpty())
            source.setText(1+"");
        else {
            int num = Integer.parseInt(source.getText());
            if (num == 9) num = 1;
            else num++;
            source.setText(num + "");

        }
        System.out.println();


    }
}
