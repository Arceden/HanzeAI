package Views;

import GameModes.TicTacToe;
import javafx.scene.control.Cell;

import javax.swing.*;
import java.awt.*;

public class TicTacToeView extends JFrame {



    public TicTacToeView()
    {

    }


    public void setTestValue(String value)
    {
        System.out.println(value);
    }

    public void TicTacToeFrame(int cell[][])
    {
        JPanel panel = new JPanel(new GridLayout(3, 3, 0, 0));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                //panel.add(cell[i][j] = new cell());
            }
        }

        add(panel, BorderLayout.CENTER);
    }




}
