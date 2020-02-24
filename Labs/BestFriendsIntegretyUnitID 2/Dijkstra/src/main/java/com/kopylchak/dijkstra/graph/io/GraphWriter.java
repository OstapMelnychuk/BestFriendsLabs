package com.kopylchak.dijkstra.graph.io;

import com.kopylchak.dijkstra.graph.Graph;
import java.io.OutputStream;

public interface GraphWriter {
    void writeGraph(Graph graph, OutputStream outputStream);
}
