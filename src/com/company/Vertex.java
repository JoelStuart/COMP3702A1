package com.company;

import java.util.HashSet;

/**
 * Created by Stuey on 24/08/2016.
 */
public class Vertex implements Comparable<Vertex>{
    private final int loc;
    private double dist;
    private double f;
    private Vertex prev;
    private HashSet<Edge> edges;

    public Vertex(int loc)  {
        this.loc = loc;
        this.dist = Double.MAX_VALUE;
        this.prev = null;
        this.edges = null;
    }

    public int getLoc() {
        return loc;
    }

    public double getDist() {
        return this.dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public Vertex getPrev() {
        return prev;
    }

    public double getF() {
        return f;
    }

    public void setF(double n) {
        this.f = n;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public boolean hasEdges(){
        return this.edges.equals(null);
    }

    public void setEdges(HashSet<Edge> e) {
        this.edges = e;
    }

    public void addEdges(Edge edge) {
        if (edges == null){
            edges = new HashSet<Edge>();
        }
        edge.setA(this);
        edges.add(edge);
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    @Override
    public int hashCode()
    {
        return loc;
    }

    @Override
    public boolean equals( Object obj )
    {
        return obj.hashCode() == this.loc;
    }

    @Override
    public int compareTo( Vertex x){
       /* if (this.dist < x.getDist()){
            return -1;
        } else if (this.dist > x.getDist()){
            return 1;
        } else {
            return 0;
        }*/
        return Double.compare(this.dist, x.getDist());
    }

    public String toString(){
        return Integer.toString(loc);
    }
}
