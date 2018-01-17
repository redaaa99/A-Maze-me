package com.projet.labyrinthe;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

public abstract class PathFinding extends AbstractSolver{
		 
		 List<Integer> parents = new ArrayList<Integer>();
		 List<Integer> childrens = new ArrayList<Integer>();
		 boolean pathFound = false;
		 
		 public PathFinding(int width, int height, int i, int j, int ia, int ja, List<Cell> grid, Group tilegroupe) {
			    super(width,height,i,j,ia,ja,grid,tilegroupe);
		        pathFound = false;
		        parents.add(-1);
		        childrens.add(getIndex(i,j));
		 }
		 public abstract void testNeighbors(int indexCurr, int indexNeighbor , int wall);
		 public void markThePath() {
			 //List<Integer> path = new ArrayList<Integer>();
             //path.add(getIndex(ia,ja));
             grid.get(getIndex(ia,ja)).setVisited(false);
             int a = childrens.indexOf(getIndex(ia,ja));
             while(parents.get(a)!=-1) {
                 //path.add(parents.get(a));
                 grid.get(parents.get(a)).setVisited(false);
                 a = childrens.indexOf(parents.get(a));
             }
		 }
}
