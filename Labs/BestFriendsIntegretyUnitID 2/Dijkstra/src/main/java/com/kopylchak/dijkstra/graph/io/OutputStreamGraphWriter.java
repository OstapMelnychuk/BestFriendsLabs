package com.kopylchak.dijkstra.graph.io;

import com.google.inject.Singleton;
import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.Vertex;
import java.io.*;
import java.util.Iterator;

@Singleton
public class OutputStreamGraphWriter implements GraphWriter {
    private static final String FROM_VERTEX_FORMAT = "%s -> ";
    private static final String TO_VERTEX_FORMAT = "%s(%d), ";

    @Override
    public void writeGraph(Graph graph, OutputStream outputStream) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            Iterator<Vertex> iterator = graph.getVertexes().iterator();

            while (iterator.hasNext()) {
                writeVertex(iterator.next(), graph, writer, !iterator.hasNext());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeVertex(Vertex vertex, Graph graph, BufferedWriter writer, boolean isLast) throws IOException {
        StringBuilder line = new StringBuilder(String.format(FROM_VERTEX_FORMAT, vertex.getName()));

        for (Vertex v : vertex.getNeighbors().keySet()) {
            line.append(String.format(TO_VERTEX_FORMAT, v.getName(), graph.getDistanceBetween(vertex, v)));
        }

        line.delete(line.length() - 2, line.length());

        writer.write(line.toString() + (isLast ? "" : "\r\n"));
    }
}
