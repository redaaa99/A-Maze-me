package com.projet.labyrinthe;

import javafx.scene.Group;

import java.util.Stack;

public class DFSBackTrackerGenerator extends AbstractMazeGenerator{
    Stack<Cell> myStack = new Stack<Cell>();
    public DFSBackTrackerGenerator(int rows, int cols,Group tilegroup) {
        super(rows, cols,tilegroup);
    }
    public DFSBackTrackerGenerator generateMaze() {
        current = grid.get(0);
        myStack.push(current);
        while(!myStack.empty()) {
            current.setVisited(true);
            //STEP 1
            Cell nextNeighbor = checkNeighbors(current);
            if(nextNeighbor != null) {
                nextNeighbor.setVisited(true);
                //STEP 2
                myStack.push(current);
                //STEP 3
                current.removeWalls(nextNeighbor);
                //STEP 4
                current = nextNeighbor;
            } else if(!myStack.empty()){
                current = myStack.pop();
            }
        }
        return this;
    }
  }