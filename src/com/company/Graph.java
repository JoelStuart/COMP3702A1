package com.company;

import java.util.*;
import java.util.Iterator;

/**
 * Created by Stuey on 22/08/2016.
 */
public class Graph  {
    //private final Map<Vertex, Set<Edge>> G = new HashMap<Vertex, Set<Edge>>();
    private ArrayList<Vertex> G;

    public Graph(int size){
        G = new ArrayList<Vertex>(size);
    }

    public void addVertex(Vertex v){
        G.add(v);
    }

    public void addEdge(Vertex start, Vertex dest, double weight, int i){
        Edge e = new Edge(start, dest, weight);
        start.addEdges(e);
        //G.set(i-1, start);
    }


    public HashSet<Edge> getEdges(Vertex v){
        return G.get(v.getLoc()-1).getEdges();
    }

    public Vertex getVertex(Vertex v) {
        return G.get(v.getLoc()-1);
    }

    public Vertex getVertex(int loc) {
        return G.get(loc-1);
    }

    public int size() {
        return G.size();
    }
    public boolean isEmpty() {
        return (G.size() == 0);
    }
}
