package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class AStarSolver extends AbstractSolver{
    List<Integer> parents = new ArrayList<Integer>();
    List<Integer> childrens = new ArrayList<Integer>();
    List<Cell> closedList = new ArrayList<Cell>();
    List<Cell> openList = new ArrayList<Cell>();
    List<Integer> scors = new ArrayList<Integer>();
    boolean pathFound = false;
    int scor = 0;
    int i,j;
    int posMin;

    public AStarSolver(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe) {
        super(width, height, i, j, ia, ja, grid, tilegroupe);

        this.i = i;
        this.j = j;
        parents.add(-1);
        childrens.add(0);
        openList.add(grid.get(0));
        scors.add(scor);


        if(Controller.animationSpeed==0) {
            while (!openList.isEmpty()) {
                posMin = getLowestScorePos(scors);
                current = openList.get(posMin);

                if (current.getRow() == ia && current.getCol() == ja) {
                    pathFound = true;
                    break;
                }
                OneIteration();
            }
            if (pathFound) {
                List<Integer> path = new ArrayList<Integer>();
                path.add(getIndex(ia,ja));
                grid.get(getIndex(ia,ja)).setVisited(false);
                int a = childrens.indexOf(getIndex(ia,ja));
                while(parents.get(a)!=-1) {
                    path.add(parents.get(a));
                    grid.get(parents.get(a)).setVisited(false);
                    a = childrens.indexOf(parents.get(a));
                }
                drawPath();
            }
        }else if(Controller.animationSpeed==2)
        {
            pathFound = false;
            at = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    int posMin = getLowestScorePos(scors);
                    current = openList.get(posMin);
                    if (current.getRow() == ia && current.getCol() == ja) {
                        pathFound = true;
                        List<Integer> path = new ArrayList<Integer>();
                        path.add(getIndex(ia,ja));
                        grid.get(getIndex(ia,ja)).setVisited(false);
                        int a = childrens.indexOf(getIndex(ia,ja));
                        while(parents.get(a)!=-1) {
                            path.add(parents.get(a));
                            grid.get(parents.get(a)).setVisited(false);
                            a = childrens.indexOf(parents.get(a));
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

    @Override
    public void OneIteration() {
        int index = getIndex(current.getRow(), current.getCol());
        int indexTop = getIndex(current.getRow() - 1, current.getCol());
        int indexRight = getIndex(current.getRow(), current.getCol() + 1);
        int indexBottom = getIndex(current.getRow() + 1, current.getCol());
        int indexLeft = getIndex(current.getRow(), current.getCol() - 1);

        Cell top = (indexTop != -1) ? grid.get(indexTop) : null;
        Cell right = (indexRight != -1) ? grid.get(indexRight) : null;
        Cell bottom = (indexBottom != -1) ? grid.get(indexBottom) : null;
        Cell left = (indexLeft != -1) ? grid.get(indexLeft) : null;

        if (top != null && !current.getPosWalls(0) && !closedList.contains(top)) {
            scor = calculateScor(top.getRow(), top.getCol(), i, j) + scors.get(posMin) + 1;
            if (!openList.contains(top)) {
                parents.add(index);
                childrens.add(indexTop);
                openList.add(top);
                scors.add(scor);
            } else if (scor < scors.get(openList.indexOf(top))) {
                parents.set(parents.indexOf(indexTop), index);
                scors.set(openList.indexOf(top), scor);
            }
        }
        if (right != null && !current.getPosWalls(1) && !closedList.contains(right)) {
            scor = calculateScor(right.getRow(), right.getCol(), i, j) + scors.get(posMin) + 1;
            if (!openList.contains(right)) {
                parents.add(index);
                childrens.add(indexRight);
                openList.add(right);
                scors.add(scor);
            } else if (scor < scors.get(openList.indexOf(right))) {
                parents.set(parents.indexOf(indexRight), index);
                scors.set(openList.indexOf(top), scor);
            }
        }
        if (bottom != null && !current.getPosWalls(2) && !closedList.contains(bottom)) {
            scor = calculateScor(bottom.getRow(), bottom.getCol(), i, j) + scors.get(posMin) + 1;
            if (!openList.contains(bottom)) {
                parents.add(index);
                childrens.add(indexBottom);
                openList.add(bottom);
                scors.add(scor);
            } else if (scor < scors.get(openList.indexOf(bottom))) {
                parents.set(parents.indexOf(indexBottom), index);
                scors.set(openList.indexOf(bottom), scor);
            }
        }
        if (left != null && !current.getPosWalls(3) && !closedList.contains(left)) {
            scor = calculateScor(left.getRow(), left.getCol(), i, j) + scors.get(posMin) + 1;
            if (!openList.contains(left)) {
                parents.add(index);
                childrens.add(indexLeft);
                openList.add(left);
                scors.add(scor);
            } else if (scor < scors.get(openList.indexOf(left))) {
                parents.set(parents.indexOf(indexLeft), index);
                scors.set(openList.indexOf(left), scor);
            }
        }
        scors.remove(openList.indexOf(current));
        openList.remove(current);
        closedList.add(current);
        String helper;
        helper = tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).getStyle();
        helper = helper.replace("#FFF", "#77abff");
        tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).setStyle(helper);

    }
    public int getLowestScorePos(List<Integer> scors) {
        int posMin = 0;
        for(int i=1 ; i<scors.size() ; i++) {
            if(scors.get(i)<=scors.get(posMin)) posMin = i;
        }
        return posMin;
    }
    public int calculateScor(int starti , int startj , int endi , int endj) {
        return Math.abs(endi-starti) + Math.abs(endj-startj);
    }
}
