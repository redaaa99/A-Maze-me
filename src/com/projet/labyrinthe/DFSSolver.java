package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import java.util.*;

public class DFSSolver extends PathFinding{
    public Stack<Cell> dfsStack = new Stack<>();
   
    public boolean pathFound = false;
    public DFSSolver(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe)
    {
        super(width,height,i,j,ia,ja,grid,tilegroupe);
        current = grid.get(getIndex(i,j));
        dfsStack.push(current);
        solve();
    }
    public void solve() {
        if(Controller.animationSpeed==0) {
            while (!dfsStack.empty()) {
                if ((current.getRow() == ia && current.getCol() == ja)|| dfsStack.empty()) {
                    if(current.getRow() == ia && current.getCol() == ja) {
                        pathFound = true;
                        markThePath();
                        drawPath();
                    }
                    break;
                }
                OneIteration();
            }
        }else if(Controller.animationSpeed==2) {
            at = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if ((current.getRow() == ia && current.getCol() == ja)|| dfsStack.empty()) {
                        if(current.getRow() == ia && current.getCol() == ja) {
                            pathFound = true;
                            markThePath();
                            drawPath();
                        }
                        this.stop();
                    }
                    OneIteration();
                }
            };
            at.start();
        }
    }
    public void OneIteration()
    {
        current = dfsStack.pop();
        current.setVisited(true);
        int index = getIndex(current.getRow(), current.getCol());
        int indexTop = getIndex(current.getRow() - 1, current.getCol());
        int indexRight = getIndex(current.getRow(), current.getCol() + 1);
        int indexBottom = getIndex(current.getRow() + 1, current.getCol());
        int indexLeft = getIndex(current.getRow(), current.getCol() - 1);
        testNeighbors(index, indexTop , 0);
        testNeighbors(index, indexRight , 1);
        testNeighbors(index, indexBottom , 2);
        testNeighbors(index, indexLeft , 3);
        drawCell();
    }
	@Override
	public void testNeighbors(int indexCurr, int indexNeighbor, int wall) {
		Cell neighbor = (indexNeighbor != -1) ? grid.get(indexNeighbor) : null;
		if (neighbor != null && !current.getPosWalls(wall) && !neighbor.isProcessed()) {
			neighbor.setProcessed(true);
            parents.add(indexCurr);
            childrens.add(indexNeighbor);
            dfsStack.push(neighbor);
        }
	}
}
