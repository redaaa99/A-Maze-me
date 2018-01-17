package com.projet.labyrinthe;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public abstract class MinPathFinding extends PathFinding{
    List<Cell> closedList = new ArrayList<Cell>();
    List<Cell> openList = new ArrayList<Cell>();
    List<Integer> scors = new ArrayList<Integer>();
    boolean pathFound = false;
    int posMin ;

    public MinPathFinding(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe) {
        super(width, height, i, j, ia, ja, grid, tilegroupe);
        openList.add(grid.get(getIndex(i,j)));
        scors.add(0);
    }
    public void OneIteration() {
        int index = getIndex(current.getRow(), current.getCol());
        int indexTop = getIndex(current.getRow() - 1, current.getCol());
        int indexRight = getIndex(current.getRow(), current.getCol() + 1);
        int indexBottom = getIndex(current.getRow() + 1, current.getCol());
        int indexLeft = getIndex(current.getRow(), current.getCol() - 1);
        testNeighbors(index, indexTop , 0);
        testNeighbors(index, indexRight , 1);
        testNeighbors(index, indexBottom , 2);
        testNeighbors(index, indexLeft , 3);
        scors.remove(openList.indexOf(current));
        openList.remove(current);
        closedList.add(current);
        drawCell();
    }

    public int getLowestScorePos(List<Integer> scors) {
        int posMin = 0;
        for(int i=1 ; i<scors.size() ; i++) {
            if(scors.get(i)<=scors.get(posMin)) posMin = i;
        }
        return posMin;
    }
}
