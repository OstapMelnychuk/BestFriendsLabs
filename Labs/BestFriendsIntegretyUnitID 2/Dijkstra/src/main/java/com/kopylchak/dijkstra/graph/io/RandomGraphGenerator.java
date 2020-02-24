package com.kopylchak.dijkstra.graph.io;

import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.GraphImpl;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.Random;
import javax.inject.Singleton;

@Singleton
public class RandomGraphGenerator implements GraphGenerator {
    private Random random;

    public RandomGraphGenerator() {
        this.random = new Random();
    }

    @Override
    public Graph generate(long vertexNumber) {
        Graph graph = new GraphImpl();

        fillWithVertexes(graph, vertexNumber);
        fillWithEdges(graph);

        return graph;
    }

    @Override
    public String randomVertex(Graph graph) {
        int randomNumber = random.nextInt(graph.getVertexes().size());

        int i = 0;
        for (Vertex v : graph.getVertexes()) {
            if (i++ == randomNumber) {
                return v.getName();
            }
        }

        throw new RuntimeException();
    }

    private void fillWithVertexes(Graph graph, long vertexNumber) {
        for (int i = 0; i < vertexNumber; i++) {
            graph.addVertex(new Vertex("v" + i));
        }
    }

    private void fillWithEdges(Graph graph) {
        for (Vertex from : graph.getVertexes()) {
            for (Vertex to : graph.getVertexes()) {
                if (!from.equals(to)) {
                    from.addNeighbor(to, random.nextInt(Integer.MAX_VALUE));
                }
            }
        }
    }
}