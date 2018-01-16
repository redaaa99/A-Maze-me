package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import java.util.List;

public class WallFollower extends AbstractSolver{
    public WallFollower(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe)
    {
        super(width,height,i,j,ia,ja,grid,tilegroupe);
        previous = grid.get(getIndex(i,j));
        int indexTop =  getIndex( previous.getRow()-1, previous.getCol());
        int indexRight =  getIndex( previous.getRow() , previous.getCol()+1);
        int indexBottom =  getIndex( previous.getRow()+1, previous.getCol());
        int indexLeft =  getIndex( previous.getRow() , previous.getCol()-1);

        Cell top = (indexTop!=-1) ? grid.get(indexTop) : null;
        Cell right = (indexRight!=-1) ? grid.get(indexRight) : null;
        Cell bottom = (indexBottom!=-1) ? grid.get(indexBottom) : null;
        Cell left = (indexLeft!=-1) ? grid.get(indexLeft) : null;

        if(left!=null && !previous.getPosWalls(3)) {
            current = left;
        }
        else if(bottom!=null && !previous.getPosWalls(2)) {
            current = bottom;
        }
        else if(right!=null && !previous.getPosWalls(1)) {
            current = right;
        }
        else if(top!=null && !previous.getPosWalls(0)) {
            current = top;
        }
        if(Controller.animationSpeed==0) {

            while (!(current.getRow() == ia && current.getCol() == ja)) {
                OneIteration();
            }
            drawPath();
        } else if(Controller.animationSpeed==2)
        {
            at = new AnimationTimer(){
                private long lastUpdate = System.nanoTime(); ;
                @Override
                public void handle(long now) {
                    OneIteration();
                    if (current.getRow() == ia && current.getCol() == ja) {
                        drawPath();
                        this.stop();
                    }
                }
            };
            at.start();
        }

    }

    public void OneIteration()
    {
        if (!current.isVisited()) {
            previous.setVisited(true);
        }
        current.setVisited(false);
        int y = current.getRow() - previous.getRow();
        int x = current.getCol() - previous.getCol();
        if (x == 1) {
            int[] priority = {2, 1, 0, 3};
            setCurandPrev(priority);
        } else if (x == -1) {
            int[] priority = {0, 3, 2, 1};
            setCurandPrev(priority);
        }
        if (y == 1) {
            int[] priority = {3, 2, 1, 0};
            setCurandPrev(priority);
        } else if (y == -1) {
            int[] priority = {1, 0, 3, 2};
            setCurandPrev(priority);
        }
        String helper;
        helper = tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).getStyle();
        helper = helper.replace("#FFF", "#77abff");
        tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).setStyle(helper);
    }
}