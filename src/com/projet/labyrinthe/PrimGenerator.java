package com.projet.labyrinthe;

import javafx.scene.Group;
import java.util.*;

public class PrimGenerator extends AbstractMazeGenerator{
    List<Cell> listOfWalls = new ArrayList<Cell>();
    
    public PrimGenerator(int rows, int cols, Group tileGroup) {
        super(rows,cols,tileGroup);
    }
    public void setListOfWalls(Cell cell) {

        int indexLeft =  getIndex( cell.getRow(), cell.getCol()-1);
        int indexBottom=  getIndex( cell.getRow()+1 , cell.getCol());
        int indexRight=  getIndex( cell.getRow(), cell.getCol()+1);
        int indexTop=  getIndex( cell.getRow()-1 , cell.getCol());

        Cell top = (indexTop!=-1) ? grid.get(indexTop) : null;
        Cell right = (indexRight!=-1) ? grid.get(indexRight) : null;
        Cell bottom = (indexBottom!=-1) ? grid.get(indexBottom) : null;
        Cell left = (indexLeft!=-1) ? grid.get(indexLeft) : null;
        if(top!=null && !top.isVisited() && !listOfWalls.contains(top)) {
            listOfWalls.add(top);
        }
        if(right!=null && !right.isVisited() && !listOfWalls.contains(right)) {
            listOfWalls.add(right);
        }
        if(bottom!=null && !bottom.isVisited() && !listOfWalls.contains(bottom)) {
            listOfWalls.add(bottom);
        }
        if(left!=null && !left.isVisited() && !listOfWalls.contains(left)) {
            listOfWalls.add(left);
        }
    }
    //Methodes
    public AbstractMazeGenerator generateMaze() {
        int r = (int) (Math.random() * grid.size());
        Cell current = grid.get(r);
        this.setListOfWalls(current);
        current.setVisited(true);
        while(!listOfWalls.isEmpty()){
            //STEP 1
            r = (int) (Math.random() * listOfWalls.size());
            current = listOfWalls.get(r);
            current.removeWalls(checkVisitedNeighbors(current));
            this.setListOfWalls(current);
            //STEP 2
            listOfWalls.remove(r);
            //Step 3
            current.setVisited(true);
        }
        return this;
    }
    public int getIndex(int i, int j) {
        return (i<0 || j<0 || j>width-1 || i>height-1) ?  -1 : (j + i*width);
    }
}