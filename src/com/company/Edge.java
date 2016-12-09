package com.company;

/**
 *
 */
public class Edge {

    private Vertex a;
    private Vertex b;
    private final double weight;

    public Edge(Vertex a, Vertex b, double weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    /**
     * Returns a
     */
    public Vertex getA() {
        return a;
    }

    /**
     * Returns b
     */
    public Vertex getB() {
        return b;
    }

    public double costA(){
        return a.getDist();
    }

    public double costB(){
        return a.getDist();
    }
    public void setA(Vertex a) {
        this.a = a;
    }

    /**
     * Returns weight
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals( Object obj )
    {
        return obj.hashCode() == this.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(a);
        sb.append(",");
        sb.append(b);
        return sb.toString();
    }

}
