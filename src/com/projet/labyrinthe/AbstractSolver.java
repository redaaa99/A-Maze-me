package com.projet.labyrinthe;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolver extends AbstractMaze{
    protected List<Cell> grid = new ArrayList<Cell>();
    protected Cell current;
    protected int ia,ja;
    protected int i,j;
    public AnimationTimer at;
    public Timer timer;
        
    public abstract void solve();
    public abstract void OneIteration();
    public AnimationTimer getAt() {
	        return at;
    }
    public AbstractSolver (int width, int height, int i , int j,int ia , int ja, List<Cell> currGrid, Group tilegroup) {
	            super(height,width,tilegroup);
	            this.ia = ia;
	            this.ja = ja;
	            this.i = i;
	            this.j = j;
	            grid  = currGrid;
	        }
    public List<Cell> getMaze() {
	        return grid;
	    }
    public void drawPath() {
	    for (int x = 0; x < grid.size(); x++) {
	        if(!grid.get(x).isVisited())
	        {
	            String helper;
	            helper = tilegroup.getChildren().get(x).getStyle();
	            helper = helper.replace("#a3c9f8", "#0b7c7c");
	            tilegroup.getChildren().get(x).setStyle(helper);
	        }/*else {
	        		String helper;
	            helper = tilegroup.getChildren().get(x).getStyle();
	            helper = helper.replace("#a3c9f8", "#FFF");
	            tilegroup.getChildren().get(x).setStyle(helper);
	        }*/
	    }
	}
    public Timer getTimer() {
        return timer;
    }
    public void drawCell() {
    		String helper;
        helper = tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).getStyle();
        helper = helper.replace("#FFF", "#a3c9f8");
        tilegroup.getChildren().get(getIndex(current.getRow(), current.getCol())).setStyle(helper);
    }
}
