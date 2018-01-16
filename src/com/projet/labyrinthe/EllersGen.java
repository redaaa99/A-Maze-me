package com.projet.labyrinthe;

import javafx.scene.Group;
import java.util.*;


public class EllersGen extends AbstractMazeGenerator {
    private final DisjointSets disjointSet = new DisjointSets();
    Random r = new Random();


    public EllersGen(int rows, int cols,Group tilegroup) {
        super(rows, cols,tilegroup);
    }
    public EllersGen generateMaze() {
        /*for (int i = 0; i < width; i++) {
            for(int j=0;j<width;j++) {
                Cell c = new Cell(i,j);
                c.setVisited(false);
                grid.add(c);
            }
        }*/
        for (int i = 0; i < grid.size(); i++) {
            grid.get(i).setIdforEller(i);
            disjointSet.create_set(grid.get(i).getIdforEller());
        }
        int from = 0;
        int to = width;
        for(int i=0;i<width-1;i++)
        {
            joinCellsInRow(from,to);
            from = to;
            to += width;
        }
        to = from;
        from = from-width;
        for(int i=from;i<to-1;i++)
        {
            if(grid.get(i).getIdforEller()!=grid.get(i+1).getIdforEller())
            {
                grid.get(i+1).setIdforEller(grid.get(i).getIdforEller());
                disjointSet.union(grid.get(i).getIdforEller(),grid.get(i+1).getIdforEller());
                grid.get(i).removeWalls(grid.get(i+1));
            }
        }
        for(int i=width*width-width;i<width*width-1;i++)
        {
            grid.get(i).setVisited(true);
            if(r.nextBoolean() && grid.get(i).getIdforEller()!=grid.get(i+1).getIdforEller())
            {
                grid.get(i+1).setIdforEller(grid.get(i).getIdforEller());
                disjointSet.union(grid.get(i).getIdforEller(),grid.get(i+1).getIdforEller());
                grid.get(i).removeWalls(grid.get(i+1));
            }
        }
        //grid.get(0).removeWalls(grid.get(1));
        //grid.get(0).removeWalls(grid.get(width));
        //grid.get(grid.size()-1).removeWalls(grid.get(grid.size()-2));
        grid.get(grid.size()-1).removeWalls(grid.get(grid.size()-1-width));
        return this;
    }
    public void joinCellsInRow(int from,int to) {
        for(int i=from;i<to-1;i++)
        {
            grid.get(i).setVisited(true);

            if(r.nextBoolean() && grid.get(i).getIdforEller()!=grid.get(i+1).getIdforEller())
            {
                grid.get(i+1).setIdforEller(grid.get(i).getIdforEller());
                disjointSet.union(grid.get(i).getIdforEller(),grid.get(i+1).getIdforEller());
                grid.get(i).removeWalls(grid.get(i+1));
            }
        }
        for(int i=from;i<to;i++)
        {
            List<Cell> set = setTolist(grid.get(i).getIdforEller(),from,to);
            while(isNotDone(set))
            {
             //   do {
                    int off = r.nextInt(set.size());
                    //System.out.println("Size : "+set.size());
                    //System.out.println(off);
                    grid.get(i+off+width).setIdforEller(grid.get(i+off).getIdforEller());
                    disjointSet.union(grid.get(i+off).getIdforEller(),grid.get(i+off+width).getIdforEller());
                    grid.get(i+off).removeWalls(grid.get(i+off+width));
               // }while(r.nextBoolean());
            }
        }
    }
    public boolean isNotDone(List<Cell> set){
        for(Cell x:set) {
            int top = x.getIdforEller();
            Cell downCell = x.getBottomNeighbour(grid);
            int down = downCell.getIdforEller();
            if (top==down) {
                return false;
            }
        }
        return true;
    }
    public List<Cell> setTolist(int n,int from,int to)
    {
        List<Cell> set = new ArrayList<>();
        for(int i=from;i<to;i++)
        {
            if(grid.get(i).getIdforEller()==disjointSet.find_set(n))
            {
                set.add(grid.get(i));
            }
        }
        return set;
    }
}