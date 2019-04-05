package Views;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class TicTacToeView extends Pane {

    ArrayList<Rectangle> cells = new ArrayList<>();
    BorderPane pane;

    public TicTacToeView(BorderPane pane)
    {
        this.pane = pane;


        GridPane gr = new GridPane();
        Button btn = new Button();
        btn.setPrefHeight(50);
        btn.setPrefWidth(50);


        for (int i = 0; i < 9; i++)
        {
            Rectangle rect = new Rectangle(50, 50, 50, 50);

            if(i%2==1)
            {
                rect.setFill(Color.BLACK);
            }

            else
            {
                rect.setFill(Color.GREY);
            }
            cells.add(rect);

            gr.add(rect, i%3, i/3);
        }
    }


    public void setTestValue(String value)
    {
        System.out.println(value);
    }





}
