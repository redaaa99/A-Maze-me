package com.projet.labyrinthe;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class KruskalMazeGenerator extends AbstractMazeGenerator{
    private List<Cell> gridForKruskal = new ArrayList<Cell>();
    int[] parents;
    //Constructeurs
    public KruskalMazeGenerator(int rows, int cols,Group tileGroup) {
        super(rows,cols,tileGroup);
        for(int i=0 ; i<height ; i++) {
            for(int j=0 ; j<width ; j++) {
                gridForKruskal.add(grid.get(getIndex(i,j)));
            }
        }
        parents= new int[height*width];
    }
    //methods
    public AbstractMazeGenerator generateMaze() {
        makeSet();
        while(!gridForKruskal.isEmpty()) {
            //System.out.println(gridForKruskal.size());
            int r = (int) (Math.random() * gridForKruskal.size());
            current = gridForKruskal.get(r);
            Cell nextNeighbor = getExternNeighbor(current);

            if(nextNeighbor!=null) {
                current.removeWalls(nextNeighbor);
                int index = getIndex( current.getRow(), current.getCol());
                int indexN = getIndex( nextNeighbor.getRow(), nextNeighbor.getCol());
                joinSets(index,indexN);
            }
            else {
                current.setVisited(true);
                gridForKruskal.remove(current);
            }
        }
        return this;
    }
    //Sets
    public void makeSet() {
        for(int i=0; i<parents.length ; i++) {
            parents[i] = i;
        }
    }
    public int find(int x)
    {
        while( x != parents[x] ) {
            x=parents[x];
        }
        return parents[x];
    }
    public void joinSets(int a, int b)
    {
        int parentA = find(a);
        int parentB = find(b);
        if (parentA == parentB)
            return;
        parents[parentA] = parentB;
    }
    //Neighbors
    public Cell getExternNeighbor(Cell cell) {
        List<Cell> neighbors = new ArrayList<Cell>();
        int index = getIndex( cell.getRow(), cell.getCol());
        int indexLeft =  getIndex( cell.getRow(), cell.getCol()-1);
        int indexBottom=  getIndex( cell.getRow()+1 , cell.getCol());
        int indexRight=  getIndex( cell.getRow(), cell.getCol()+1);
        int indexTop=  getIndex( cell.getRow()-1 , cell.getCol());

        Cell top = (indexTop!=-1) ? grid.get(indexTop) : null;
        Cell right = (indexRight!=-1) ? grid.get(indexRight) : null;
        Cell bottom = (indexBottom!=-1) ? grid.get(indexBottom) : null;
        Cell left = (indexLeft!=-1) ? grid.get(indexLeft) : null;

        if(top!=null && find(index)!=find(indexTop) ) {
            neighbors.add(top);
        }
        if(right!=null && find(index)!=find(indexRight)) {
            neighbors.add(right);
        }
        if(bottom!=null && find(index)!=find(indexBottom)) {
            neighbors.add(bottom);
        }
        if(left!=null && find(index)!=find(indexLeft)) {
            neighbors.add(left);
        }
        if(neighbors.size()>0) {
            int r = (int) (Math.random() * neighbors.size());
            return neighbors.get(r);
        }else {
            return null;
        }
    }
}
