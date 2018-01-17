package com.projet.labyrinthe;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMazeGenerator extends AbstractMaze{
    protected List<Cell> grid = new ArrayList<Cell>();
    protected Cell current;
    public AbstractMazeGenerator(int rows, int cols, Group tilegroup) {
    		super(rows,cols,tilegroup);
        grid.clear();
        for(int i=0 ; i<height ; i++) {
            for(int j=0 ; j<width ; j++) {
                Cell newCell = new Cell(i,j);
                grid.add(newCell);
            }
        }
    }
    public abstract AbstractMazeGenerator generateMaze();
    public List<Cell> getMaze() {
        return grid;
    }
    public Cell checkVisitedNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<Cell>();
        int indexLeft =  getIndex( cell.getRow(), cell.getCol()-1);
        int indexBottom=  getIndex( cell.getRow()+1 , cell.getCol());
        int indexRight=  getIndex( cell.getRow(), cell.getCol()+1);
        int indexTop=  getIndex( cell.getRow()-1 , cell.getCol());

        Cell top = (indexTop!=-1) ? grid.get(indexTop) : null;
        Cell right = (indexRight!=-1) ? grid.get(indexRight) : null;
        Cell bottom = (indexBottom!=-1) ? grid.get(indexBottom) : null;
        Cell left = (indexLeft!=-1) ? grid.get(indexLeft) : null;
        if(top!=null && top.isVisited()) {
            neighbors.add(top);
        }
        if(right!=null && right.isVisited()) {
            neighbors.add(right);
        }
        if(bottom!=null && bottom.isVisited()) {
            neighbors.add(bottom);
        }
        if(left!=null && left.isVisited()) {
            neighbors.add(left);
        }
        if(neighbors.size()>0) {
            int r = (int) (Math.random() * neighbors.size());
            return neighbors.get(r);
        }else {
            return null;
        }

    }
    public Cell checkNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<Cell>();
        int indexLeft =  getIndex( cell.getRow(), cell.getCol()-1);
        int  indexBottom=  getIndex( cell.getRow()+1 , cell.getCol());
        int  indexRight=  getIndex( cell.getRow(), cell.getCol()+1);
        int  indexTop=  getIndex( cell.getRow()-1 , cell.getCol());

        Cell top = (indexTop!=-1) ? grid.get(indexTop) : null;
        Cell right = (indexRight!=-1) ? grid.get(indexRight) : null;
        Cell bottom = (indexBottom!=-1) ? grid.get(indexBottom) : null;
        Cell left = (indexLeft!=-1) ? grid.get(indexLeft) : null;
        if(top!=null && !top.isVisited()) {
            neighbors.add(top);
        }
        if(right!=null && !right.isVisited()) {
            neighbors.add(right);
        }
        if(bottom!=null && !bottom.isVisited()) {
            neighbors.add(bottom);
        }
        if(left!=null && !left.isVisited()) {
            neighbors.add(left);
        }
        if(neighbors.size()>0) {
            int r = (int) (Math.random() * neighbors.size());
            return neighbors.get(r);
        }else {
            return null;
        }
    }
}
