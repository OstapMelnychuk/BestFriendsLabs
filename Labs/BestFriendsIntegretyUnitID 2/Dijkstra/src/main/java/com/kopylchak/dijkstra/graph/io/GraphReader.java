package com.kopylchak.dijkstra.graph.io;

import com.kopylchak.dijkstra.graph.Graph;
import java.io.InputStream;

public interface GraphReader {
    Graph readGraph(InputStream inputStream);
}