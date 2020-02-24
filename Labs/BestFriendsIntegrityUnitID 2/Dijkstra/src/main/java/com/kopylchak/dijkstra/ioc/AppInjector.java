package com.kopylchak.dijkstra.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.kopylchak.dijkstra.controller.ConsoleController;
import com.kopylchak.dijkstra.controller.Controller;
import com.kopylchak.dijkstra.exceptions.DefaultExceptionHandler;
import com.kopylchak.dijkstra.exceptions.ExceptionHandler;
import com.kopylchak.dijkstra.graph.algorithms.Dijkstra;
import com.kopylchak.dijkstra.graph.algorithms.DijkstraImpl;
import com.kopylchak.dijkstra.graph.algorithms.ParallelDijkstra;
import com.kopylchak.dijkstra.graph.io.*;
import com.kopylchak.dijkstra.graph.productivity.DijkstraProductivityTest;
import com.kopylchak.dijkstra.graph.productivity.DijkstraProductivityTestImpl;

public class AppInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(Controller.class)
            .to(ConsoleController.class);
        bind(ExceptionHandler.class)
            .to(DefaultExceptionHandler.class);
        bind(GraphReader.class)
            .to(InputStreamGraphReader.class);
        bind(GraphPrinter.class)
            .to(ConsoleGraphPrinter.class);
        bind(DijkstraProductivityTest.class)
            .to(DijkstraProductivityTestImpl.class);
        bind(GraphParser.class)
            .to(GraphParserImpl.class);
        bind(GraphGenerator.class)
            .to(RandomGraphGenerator.class);
        bind(Dijkstra.class)
            .annotatedWith(Names.named("dijkstraImpl"))
            .to(DijkstraImpl.class);
        bind(Dijkstra.class)
            .annotatedWith(Names.named("parallelDijkstra"))
            .to(ParallelDijkstra.class);
    }
}
