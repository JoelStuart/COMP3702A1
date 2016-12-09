package com.company;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;

public class NavApp {

    public int numV;
    public String outFile;

    public static void main(String[] args) {
        NavApp navApp = new NavApp(args);
    }

    public NavApp(String[] args) {
        if (args.length != 3) {
            System.out.print("Invalid arguments\n");
        }
        //Read in arguments

        String envFile = args[0];
        String querFile = args[1];
        //Output filename
        outFile = args[2];
        //Clear and create output file
        clearFile();
        long startTime = System.currentTimeMillis();

        //Read environemnt and create graph
        Graph G = readEnv(envFile);
        long endTime = System.currentTimeMillis();
        System.out.println("Total startup time: " + (endTime - startTime) + "ms\n");
        //Read queries and run sequentially
        readQuer(querFile, G);
        Runtime rt = Runtime.getRuntime();
        long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
        System.out.println("Memory usage: " + usedMB + "MB\n");

        //Write output to file and finish
        //write(outFile);


    }

    private Graph readEnv(String envFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(envFile));

            //Read in number of vertices
            String line = br.readLine();
            numV = Integer.parseInt(line);
            //Initialise graph with size numV
            Graph G = initGraphVertex();

            //Read next line and initialise row counter
            line = br.readLine();
            int counter = 1;

            while (line != null) {
                parseEnvLine(line, new Vertex(counter), G, counter);
                counter += 1;
                line = br.readLine();
            }
            if (counter != numV + 1) {
                System.out.print("Invalid environment file\n");
                System.exit(1);
            }
            br.close();
            return G;
        } catch (IOException e) {
            System.out.print(e);
        }
        return null;
    }

    private Graph initGraphVertex() {
        Graph G = new Graph(numV);
        for (int i = 0; i < numV; i++) {
            G.addVertex(new Vertex(i + 1));
        }
        return G;
    }

    private void parseEnvLine(String line, Vertex vertex, Graph G, int vert) {
        String delim = " ";
        String[] tokens = line.split(delim);
        int tokenCount = tokens.length;
        //For each edge weight in line
        for (int j = 0; j < tokenCount; j++) {
            //If edge exists
            double val = Double.parseDouble(tokens[j]);

            if (val != 0) {
                //Add edge between vertex and vertex j, with weight in tokens
                G.addEdge(G.getVertex(vertex), G.getVertex(j + 1), val, vert);
            }
        }
    }

    private void readQuer(String querFile, Graph G) {
        try (
                BufferedReader br = new BufferedReader(new FileReader(querFile))) {
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int q = Integer.parseInt(line);
            line = br.readLine();

            while (line != null) {
                //quer.add(line);
                parseQuerLine(line, G);
                //System.out.print(line);
                //sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();

            }
            //String everything = sb.toString();
        } catch (Exception e) {
            System.out.print("Read query exception: " + e);
        }
    }

    private void parseQuerLine(String line, Graph G) {
        String delim = " ";
        if (line.equals("")) {
            return;
        }
        String[] tokens = line.split(delim);
        //boolean uniform;
        int tokenCount = tokens.length;
        if (tokenCount != 3) {
            System.out.print("Dodgy query");
        }
        Vertex start = new Vertex(Integer.parseInt(tokens[1]));
        Vertex goal = new Vertex(Integer.parseInt(tokens[2]));

        if (tokens[0].equals("Uniform")) {
            long startTime = System.currentTimeMillis();

            if (uniformCost(start, goal, G) != true) {
                System.out.print("No path found");
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Total query time: " + (endTime - startTime) + "ms\n");
        } else if (tokens[0].equals("A*")) {
            long startTime = System.currentTimeMillis();
            if (aSearch(start, goal, G) != true) {
                System.out.print("No path found");
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Total query time: " + (endTime - startTime) + "ms\n");
        }
    }


    private boolean uniformCost(Vertex start, Vertex goal, Graph G) {
        Set<Vertex> explored = new HashSet<Vertex>();

        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();

        System.out.print("New Uniform Cost Query\n");

        Vertex root = new Vertex(start.getLoc());
        //HashSet<Edge> s = G.getEdges(root);
        root.setEdges(G.getEdges(root));
        root.setDist(0);
        pq.add(root);
        do {
            //For current node
            Vertex current = pq.poll();
            //System.out.print("Size is " + pq.size() + "\n");
            explored.add(current);
            double cDist = current.getDist();
            if (current.equals(goal)) {
                goal.setPrev(current.getPrev());
                goal.setDist(cDist);
                printPath(current);
                return true;
            }


            Set<Edge> edges = current.getEdges();

            //Set<Edge> edges = G.getEdges(current);
            for (Edge e : edges) {
                Vertex n = e.getB();
                double cost = cDist + e.getWeight();
                if (n.getDist() > (cost)) {
                    pq.remove(n);
                }
                if (!explored.contains(n) && !pq.contains(n)) {
                    n.setPrev(current);
                    n.setDist(cost);
                    pq.add(n);
                }
            }
        } while (pq.size() != 0);

        return false;
        //Expand n with lowest cost
    }

    private void printPath(Vertex goal) {

        List<Vertex> path = new ArrayList<Vertex>();
        Vertex v = goal;
        while (v != null) {
            path.add(v);
            v = v.getPrev();
        }
        Collections.reverse(path);
        StringBuilder sb = new StringBuilder("");
        Vertex l = null;
        for (Vertex x : path) {
            sb.append(x.getLoc() + "-");
            l = x;
        }
        sb.deleteCharAt(sb.length() - 1);
        //sb.append(" " + l.getDist());
        sb.append("\n");
        System.out.print(sb.toString());
        writePath(sb.toString());
    }

    private boolean aSearch(Vertex start, Vertex goal, Graph G) {

        Set<Vertex> explored = new HashSet<Vertex>();

        class MyComparator implements Comparator<Vertex> {
            @Override
            public int compare(Vertex a, Vertex b) {
                return (Double.compare(a.getF(), b.getF()));
            }
        }
        Comparator<Vertex> c = new MyComparator();
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(numV/2, c);

        System.out.print("New A* Query\n");

        Vertex root = new Vertex(start.getLoc());
        root.setEdges(G.getEdges(root));
        root.setDist(0);
        pq.add(root);
        do {
            //For current node
            Vertex current = pq.poll();
            //System.out.print("Size is " + pq.size() + "\n");
            explored.add(current);
            double cDist = current.getDist();
            if (current.equals(goal)) {
                goal.setPrev(current.getPrev());
                goal.setDist(cDist);
                printPath(goal);
                return true;
            }
            Set<Edge> edges = current.getEdges();
            if (edges == null){
                continue;
            }
            for (Edge e : edges) {

                Vertex n = e.getB();

                double cost = cDist + e.getWeight();
                if (n.getDist() > (cost)) {
                    pq.remove(n);
                }

                if (!explored.contains(n) && !pq.contains(n)) {
                    n.setPrev(current);
                    n.setDist(cost);
                    Set<Edge> ne = n.getEdges();
                    n.setF(cost + heuristic(ne, goal, 1));
                    pq.add(n);
                }
            }
        } while (pq.size() != 0);

        return false;
    }

    private double heuristic(Set<Edge> edgeSet, Vertex goal, int depth) {
        if (edgeSet == null){
            return 0;
        }
        Iterator<Edge> i = edgeSet.iterator();
        int count = 0;
        while (i.hasNext() && count < 6) {
            Edge e = i.next();
            if (e.getB().equals(goal)) {
                return e.getWeight();
            }
            count += 1;
        }
        return 0;
    }

    private void clearFile() {
        try {
            File file = new File(outFile);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePath(String output) {
        try {
            File file = new File(outFile);

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(output);

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
