package com.projet.labyrinthe;

import javafx.scene.Group;

public abstract class AbstractMaze {
	protected int width ;
    protected int height ;
    Group tilegroup;
    public AbstractMaze(int rows, int cols,Group tilegroup) {
    		height = rows;
    		width = cols;
    		this.tilegroup = tilegroup;
    }
    public int getIndex(int i, int j) {
        return (i<0 || j<0 || j>width-1 || i>height-1) ?  -1 : (j + i*width);
    }
}
