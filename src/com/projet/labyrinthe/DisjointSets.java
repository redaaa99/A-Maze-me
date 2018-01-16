package com.projet.labyrinthe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DisjointSets {

    private List<Map<Integer, Set<Integer>>> disjointSet;

    public DisjointSets() {
        disjointSet = new ArrayList<Map<Integer, Set<Integer>>>();
    }

    public void create_set(int element) {
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        Set<Integer> set = new HashSet<Integer>();

        set.add(element);
        map.put(element, set);

        disjointSet.add(map);
    }

    public void union(int first, int second) {

        int first_rep = find_set(first);
        int second_rep = find_set(second);

        Set<Integer> first_set = null;
        Set<Integer> second_set = null;

        for (int index = 0; index < disjointSet.size(); index++) {
            Map<Integer, Set<Integer>> map = disjointSet.get(index);

            if (map.containsKey(first_rep)) {
                first_set = map.get(first_rep);
            } else if (map.containsKey(second_rep)) {
                second_set = map.get(second_rep);
            }
        }

        if (first_set != null && second_set != null)
            first_set.addAll(second_set);

        for (int index = 0; index < disjointSet.size(); index++) {

            Map<Integer, Set<Integer>> map = disjointSet.get(index);

            if (map.containsKey(first_rep)) {
                map.put(first_rep, first_set);
            } else if (map.containsKey(second_rep)) {
                map.remove(second_rep);
                disjointSet.remove(index);
            }
        }
        return;
    }

    public int find_set(int element) {

        for (int index = 0; index < disjointSet.size(); index++) {
            Map<Integer, Set<Integer>> map = disjointSet.get(index);
            Set<Integer> keySet = map.keySet();

            for (Integer key : keySet) {
                Set<Integer> set = map.get(key);
                if (set.contains(element)) {
                    return key;
                }
            }
        }
        return -1;
    }

    public int getNumberofDisjointSets() {
        return disjointSet.size();
    }
}