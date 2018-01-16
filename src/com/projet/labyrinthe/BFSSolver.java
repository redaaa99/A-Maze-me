package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BFSSolver extends AbstractSolver{
    List<Integer> Queue = new ArrayList<Integer>();
    List<Integer> Origin = new ArrayList<Integer>();
    Queue<Cell> BFSqueue = new LinkedList<Cell>();
    boolean pathFound;

    public BFSSolver(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe) {
        super(width,height,i,j,ia,ja,grid,tilegroupe);
        pathFound = false;
        BFSqueue.add(grid.get(getIndex(i,j)));
        Origin.add(-1);
        Queue.add(getIndex(i,j));
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
                List<Integer> path = new ArrayList<Integer>();
                path.add(getIndex(ia,ja));
                grid.get(getIndex(ia,ja)).setVisited(false);
                int a = Queue.indexOf(getIndex(ia,ja));
                while(Origin.get(a)!=-1) {
                    path.add(Origin.get(a));
                    grid.get(Origin.get(a)).setVisited(false);
                    a = Queue.indexOf(Origin.get(a));
                }
                drawPath();
            }
        }else if(Controller.animationSpeed==2) {
            at = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    current = BFSqueue.remove();
                    current.setVisited(true);
                    if(current.getRow() == ia && current.getCol() == ja) {
                        List<Integer> path = new ArrayList<Integer>();
                        path.add(getIndex(ia,ja));
                        grid.get(getIndex(ia,ja)).setVisited(false);
                        int a = Queue.indexOf(getIndex(ia,ja));
                        while(Origin.get(a)!=-1) {
                            path.add(Origin.get(a));
                            grid.get(Origin.get(a)).setVisited(false);
                            a = Queue.indexOf(Origin.get(a));
                        }
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
        Cell top = (indexTop != -1) ? grid.get(indexTop) : null;
        Cell right = (indexRight != -1) ? grid.get(indexRight) : null;
        Cell bottom = (indexBottom != -1) ? grid.get(indexBottom) : null;
        Cell left = (indexLeft != -1) ? grid.get(indexLeft) : null;
        if (top != null && !current.getPosWalls(0) && !top.isProcessed()) {
            top.setProcessed(true);
            Origin.add(index);
            Queue.add(indexTop);
            BFSqueue.add(top);
        }
        if (right != null && !current.getPosWalls(1) && !right.isProcessed()) {
            right.setProcessed(true);
            Origin.add(index);
            Queue.add(indexRight);
            BFSqueue.add(right);
        }
        if (bottom != null && !current.getPosWalls(2) && !bottom.isProcessed()) {
            bottom.setProcessed(true);
            Origin.add(index);
            Queue.add(indexBottom);
            BFSqueue.add(bottom);
        }
        if (left != null && !current.getPosWalls(3) && !left.isProcessed()) {
            left.setProcessed(true);
            Origin.add(index);
            Queue.add(indexLeft);
            BFSqueue.add(left);
        }
        String helper;
        helper = tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).getStyle();
        helper = helper.replace("#FFF", "#77abff");
        tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).setStyle(helper);
    }
}