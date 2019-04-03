package Views;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MenuView {

    TextField tfUsername;
    Button bLogin;
    BorderPane pane;

    public MenuView(BorderPane pane){
        this.pane = pane;
    }

    public boolean GameTest(){

//        Button bLogin = new Button("Login");

        Image X = new Image("file:Prototype/Views/img/x.gif");
        Image O = new Image("file:Prototype/Views/img/o.gif");

        int gridSize = 3;
        ArrayList<Button> cells = new ArrayList<>();
        GridPane gameGrid = new GridPane();
        for (int i = 0; i < gridSize*gridSize; i++){
            Button cell = null;

            if(i%2==1)
                cell = new Button("", new ImageView(X));
            else
                cell = new Button("", new ImageView(O));

            cells.add(cell);
            gameGrid.add(cell, i%gridSize, i/gridSize);
        }

        pane.setCenter(gameGrid);


//        pane.setCenter(gr);

//        HBox hBox = new HBox(5);
//        hBox.getChildren().addAll(new Label("Username"), bLogin);

//        BorderPane menuPane = new BorderPane();
//        Scene scene = new Scene(menuPane);

//        pane.setCenter(hBox);
//
        return false;
    }

}
