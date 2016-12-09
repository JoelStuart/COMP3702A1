package com.company;

import java.util.*;
import java.util.Iterator;

/**
 * Created by Stuey on 22/08/2016.
 */
public class GraphOpt  {
    //private final Map<Vertex, Set<Edge>> G = new HashMap<Vertex, Set<Edge>>();
    private Vertex[] G;
    private int size;
    private Vertex root;

    public GraphOpt(int size){
        G = new Vertex[size];
    }

    public void addVertex(Vertex v){
        G[v.getLoc()-1] = v;
    }

    public void addEdge(Vertex start, Vertex dest, long weight){
        Edge e = new Edge(start, dest, weight);
        start.addEdges(e);
        G[start.getLoc()-1] = start;
        //Directed to no reciprical edge on dest
        //G.get(dest).add(e);

    }

    public Set<Edge> getEdges(Vertex v){
        return G[v.getLoc()-1].getEdges();
    }

    public Vertex getVertex(Vertex v) {
        return G[v.getLoc()-1];
    }

    public void setVertex(Vertex v) {
        G[v.getLoc()-1] = v;
    }

    public int size() {
        return G.length;
    }
    public boolean isEmpty() {
        return (G.length == 0);
    }
}
