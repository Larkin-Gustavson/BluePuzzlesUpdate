//Algorithm used from geeksforgeeks.com

package Controllers;

import javafx.scene.control.Label;
import java.util.*;

public class SudokuObject implements Cloneable{
    public Label mat[][] = new Label[9][9];
    int K;


    /*Constructor*/
    public SudokuObject(Label box1,Label box2,Label box3,Label box4,Label box5,Label box6,Label box7,Label box8,
                        Label box9,Label box10,Label box11,Label box12,Label box13,Label box14,Label box15,Label box16,
                        Label box17,Label box18,Label box19,Label box20,Label box21,Label box22,Label box23,Label box24,
                        Label box25,Label box26,Label box27,Label box28,Label box29,Label box30,Label box31,Label box32,
                        Label box33,Label box34,Label box35,Label box36,Label box37,Label box38,Label box39,Label box40,
                        Label box41,Label box42,Label box43,Label box44,Label box45,Label box46,Label box47,Label box48,
                        Label box49,Label box50,Label box51,Label box52,Label box53,Label box54,Label box55,Label box56,
                        Label box57,Label box58,Label box59,Label box60,Label box61,Label box62,Label box63,Label box64,
                        Label box65,Label box66,Label box67,Label box68,Label box69,Label box70,Label box71,Label box72,
                        Label box73,Label box74,Label box75,Label box76,Label box77,Label box78,Label box79,Label box80,
                        Label box81,int K){

        this.K = K;
        mat[0][0] = box1;
        mat[0][1] = box2;
        mat[0][2] = box3;
        mat[0][3] = box4;
        mat[0][4] = box5;
        mat[0][5] = box6;
        mat[0][6] = box7;
        mat[0][7] = box8;
        mat[0][8] = box9;
        mat[1][0] = box10;
        mat[1][1] = box11;
        mat[1][2] = box12;
        mat[1][3] = box13;
        mat[1][4] = box14;
        mat[1][5] = box15;
        mat[1][6] = box16;
        mat[1][7] = box17;
        mat[1][8] = box18;
        mat[2][0] = box19;
        mat[2][1] = box20;
        mat[2][2] = box21;
        mat[2][3] = box22;
        mat[2][4] = box23;
        mat[2][5] = box24;
        mat[2][6] = box25;
        mat[2][7] = box26;
        mat[2][8] = box27;
        mat[3][0] = box28;
        mat[3][1] = box29;
        mat[3][2] = box30;
        mat[3][3] = box31;
        mat[3][4] = box32;
        mat[3][5] = box33;
        mat[3][6] = box34;
        mat[3][7] = box35;
        mat[3][8] = box36;
        mat[4][0] = box37;
        mat[4][1] = box38;
        mat[4][2] = box39;
        mat[4][3] = box40;
        mat[4][4] = box41;
        mat[4][5] = box42;
        mat[4][6] = box43;
        mat[4][7] = box44;
        mat[4][8] = box45;
        mat[5][0] = box46;
        mat[5][1] = box47;
        mat[5][2] = box48;
        mat[5][3] = box49;
        mat[5][4] = box50;
        mat[5][5] = box51;
        mat[5][6] = box52;
        mat[5][7] = box53;
        mat[5][8] = box54;
        mat[6][0] = box55;
        mat[6][1] = box56;
        mat[6][2] = box57;
        mat[6][3] = box58;
        mat[6][4] = box59;
        mat[6][5] = box60;
        mat[6][6] = box61;
        mat[6][7] = box62;
        mat[6][8] = box63;
        mat[7][0] = box64;
        mat[7][1] = box65;
        mat[7][2] = box66;
        mat[7][3] = box67;
        mat[7][4] = box68;
        mat[7][5] = box69;
        mat[7][6] = box70;
        mat[7][7] = box71;
        mat[7][8] = box72;
        mat[8][0] = box73;
        mat[8][1] = box74;
        mat[8][2] = box75;
        mat[8][3] = box76;
        mat[8][4] = box77;
        mat[8][5] = box78;
        mat[8][6] = box79;
        mat[8][7] = box80;
        mat[8][8] = box81;
        /**/





    }

    public SudokuObject(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                mat[i][j] = new Label();
            }
        }
    }



    public void fillValues()
    {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, 3);

        // Remove Randomly K digits to make game
        removeKDigits();
    }

    public void makeCopy()
    {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, 3);

        // Remove Randomly K digits to make game
        //removeKDigits();
    }

    void fillDiagonal()
    {
        for (int i = 0; i<9; i=i+3)
            fillBox(i, i);
    }

    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<3; i++)
            for (int j = 0; j<3; j++) {
                if(mat[rowStart + i][colStart + j].getText().isEmpty()==false) {
                    int number = Integer.parseInt(mat[rowStart + i][colStart + j].getText());
                    if (number==num)
                        return false;
                }
            }


        return true;
    }

    void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                do
                {
                    num = randomGenerator(9);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j].setText(num+"");
                mat[row+i][col+j].disableProperty();
            }
        }
    }

    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%3, j-j%3, num));
    }

    boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<9; j++)
            if(mat[i][j].getText().isEmpty()==false) {
                int number = Integer.parseInt(mat[i][j].getText());
                if (number == num)
                    return false;
            }
        return true;
    }

    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<9; i++)
            if(mat[i][j].getText().isEmpty()==false) {
                int number = Integer.parseInt(mat[i][j].getText());
                if (number == num)
                    return false;
            }
        return true;
    }

    boolean fillRemaining(int i, int j)
    {
        //  System.out.println(i+" "+j);
        if (j>=9 && i<9-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=9 && j>=9)
            return true;

        if (i < 3)
        {
            if (j < 3)
                j = 3;
        }
        else if (i < 9-3)
        {
            if (j==(int)(i/3)*3)
                j =  j + 3;
        }
        else
        {
            if (j == 9-3)
            {
                i = i + 1;
                j = 0;
                if (i>=9)
                    return true;
            }
        }

        for (int num = 1; num<=9; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                mat[i][j].setText(num+"");
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j].setText("");
            }
        }
        return false;
    }

    public void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(81);

            int i = (cellId/9);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;

            if (mat[i][j].getText().isEmpty()==false)
            {
                count--;
                mat[i][j].setText("");
            }
        }
        for(int i=0;i<9;i++){
            int r = randomGenerator(10);
            if(r<7)
                mat[i][8].setText("");
        }
    }



    public boolean Matequals(SudokuObject so){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(so.mat[i][j].getText().equals(this.mat[i][j])==false)
                    return false;
            }
        }
        return true;
    }



}
