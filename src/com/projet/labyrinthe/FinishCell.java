package com.projet.labyrinthe;

import javafx.scene.layout.Pane;

public class FinishCell extends Pane {
    private int row;
    private int col;

    public FinishCell(int x,int y)
    {
        this.row = x;
        this.col = y;
        setWidth(Controller.cellWidth);
        setHeight(Controller.cellHeight);
        //setFill(Color.valueOf("#0F0"));
        setMaxSize(Controller.cellWidth,Controller.cellHeight);
        setMinSize(Controller.cellWidth,Controller.cellHeight);
        setPrefSize(Controller.cellWidth,Controller.cellHeight);
        setStyle("-fx-background-color: #0052a2;");
        relocate(row* Controller.cellWidth, col *Controller.cellHeight);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
