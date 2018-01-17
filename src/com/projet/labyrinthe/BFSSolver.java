package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import java.util.*;

public class BFSSolver extends PathFinding{
    Queue<Cell> BFSqueue = new LinkedList<Cell>();

    public BFSSolver(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe) {
        super(width,height,i,j,ia,ja,grid,tilegroupe);
        BFSqueue.add(grid.get(getIndex(i,j)));
        solve();
    }
    @Override
	public void solve() {
		if(Controller.animationSpeed==0)
        {
            while(!BFSqueue.isEmpty()) {
                current = BFSqueue.remove();
                current.setVisited(true);
                if (current.getRow() == ia && current.getCol() == ja) {
                    pathFound = true;
                    break;
                }
                OneIteration();
            }
            if(pathFound) {
            		markThePath();
            		drawPath();
            }
        }else if(Controller.animationSpeed==2) {
            at = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    current = BFSqueue.remove();
                    current.setVisited(true);
                    if(current.getRow() == ia && current.getCol() == ja) {
                    		markThePath();
						drawPath();
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
            BFSqueue.add(neighbor);
        }
	}
}