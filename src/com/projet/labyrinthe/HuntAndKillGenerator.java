package com.projet.labyrinthe;

import javafx.scene.Group;

import java.util.Stack;

public class HuntAndKillGenerator extends AbstractMazeGenerator{

    public HuntAndKillGenerator(int rows, int cols,Group tilegroup) {
        super(rows, cols,tilegroup);
    }
     public HuntAndKillGenerator generateMaze()
     {
         Stack<Cell> myStack = new Stack<>();
         int r = (int) (Math.random() * grid.size());
         current = grid.get(r);
         while(current != null) {
             current.setVisited(true);
             //Perform a random walk
             Cell nextNeighbor = checkNeighbors(current);
             if(nextNeighbor != null) {
                 nextNeighbor.setVisited(true);
                 myStack.push(current);
                 current.removeWalls(nextNeighbor);
                 current = nextNeighbor;
             } else {
                 //Hunt mode
                 current = huntScan();
                 if(current!=null) {
                     Cell neighbor = checkVisitedNeighbors(current);
                     if(neighbor!=null) current.removeWalls(neighbor);
                 }
             }
         }
        return this;
     }
    public Cell huntScan() {
        for(int i=0 ; i<grid.size() ; i++) {
            Cell neighbor = checkVisitedNeighbors(grid.get(i));
            if(neighbor!=null && !grid.get(i).isVisited() && neighbor.isVisited())
                return grid.get(i);
        }
        return null;
    }

}

