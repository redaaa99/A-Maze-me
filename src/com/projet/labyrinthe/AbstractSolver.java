package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolver{
        protected int width ;
        Group tilegroup;
        protected int height ;
        protected List<Cell> grid = new ArrayList<Cell>();
        protected Cell current;
        protected Cell previous;
        protected int ia;
        protected int ja;
        public AnimationTimer at;
        public Timer timer;

    public AnimationTimer getAt() {
        return at;
    }
    public abstract void OneIteration();
    public AbstractSolver (int width, int height, int i , int j,int ia , int ja, List<Cell> currGrid, Group tilegroup) {
            this.height= height;
            this.width = width;
            this.tilegroup = tilegroup;
            this.ia = ia;
            this.ja = ja;
            grid  = currGrid;
        }
    public void setCurandPrev(int[] arr) {
        previous = current;
        List<Cell> neighbors = getNeighbors(previous);
        if(neighbors.get(arr[0])!=null && !previous.getPosWalls(arr[0])) {
            current = neighbors.get(arr[0]);
        } else if(neighbors.get(arr[1])!=null && !previous.getPosWalls(arr[1])) {
            current = neighbors.get(arr[1]);
        } else if(neighbors.get(arr[2])!=null && !previous.getPosWalls(arr[2])) {
            current = neighbors.get(arr[2]);
        } else if(neighbors.get(arr[3])!=null && !previous.getPosWalls(arr[3])) {
            current = neighbors.get(arr[3]);
        }
    }
    public List<Cell> getMaze() {
        return grid;
    }
    public List<Cell> getNeighbors(Cell cell) {
    List<Cell> neighbors = new ArrayList<Cell>();

    int indexTop =  getIndex( cell.getRow()-1, cell.getCol());
    int indexRight =  getIndex( cell.getRow() , cell.getCol()+1);
    int indexBottom =  getIndex( cell.getRow()+1, cell.getCol());
    int indexLeft =  getIndex( cell.getRow() , cell.getCol()-1);

    Cell top = (indexTop!=-1) ? grid.get(indexTop) : null;
    Cell right = (indexRight!=-1) ? grid.get(indexRight) : null;
    Cell bottom = (indexBottom!=-1) ? grid.get(indexBottom) : null;
    Cell left = (indexLeft!=-1) ? grid.get(indexLeft) : null;

    neighbors.add(top);
    neighbors.add(right);
    neighbors.add(bottom);
    neighbors.add(left);

    return neighbors;
}
    public int getIndex(int i, int j) {
        return (i<0 || j<0 || j>width-1 || i>height-1) ?  -1 : (j + i*width);
    }
    public void drawPath() {
    for (int x = 0; x < grid.size(); x++) {
        if(!grid.get(x).isVisited())
        {
            String helper;
            helper = tilegroup.getChildren().get(x).getStyle();
            helper = helper.replace("#77abff", "#ff1616");
            tilegroup.getChildren().get(x).setStyle(helper);
        }
    }
}
    public Timer getTimer() {
        return timer;
    }
}
