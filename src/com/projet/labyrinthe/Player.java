package com.projet.labyrinthe;


import javafx.scene.input.KeyCode;

import javafx.scene.layout.Pane;
import java.util.List;

public class Player extends Pane {
    private int row;
    private int col;

    public Player(int x,int y) {
        this.row = y;
        this.col = x;
        setWidth(Controller.cellWidth);
        setHeight(Controller.cellHeight);
        //setFill(Color.valueOf("#0F0"));
        setMaxSize(Controller.cellWidth,Controller.cellHeight);
        setMinSize(Controller.cellWidth,Controller.cellHeight);
        setPrefSize(Controller.cellWidth,Controller.cellHeight);
        setStyle("-fx-background-color: green;");
        relocate(col* Controller.cellWidth, row *Controller.cellHeight);
    }
    public void  moveTo(KeyCode to, List<Cell> grid) {
        switch (to)
        {
            case Z:
                if(!grid.get(Controller.getIndex(col,row)).getPosWalls(0))
                {
                    setPosition(row,col-1);
                }
                break;
            case S:
                if(!grid.get(Controller.getIndex(col,row)).getPosWalls(2))
                {
                    setPosition(row,col+1);
                }
                break;
            case D:
                if(!grid.get(Controller.getIndex(col,row)).getPosWalls(1))
                {
                    setPosition(row+1,col);
                }
                break;
            case Q:
                if(!grid.get(Controller.getIndex(col,row)).getPosWalls(3))
                {
                    setPosition(row-1,col);
                }
                break;
            default:
                break;
        }
    }
    public void setPosition(int newRow,int newCol) {
        this.row = newRow;
        this.col = newCol;
        relocate(row * Controller.cellWidth, col * Controller.cellHeight);
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }
}
