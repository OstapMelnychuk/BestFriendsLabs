package com.kopylchak.dijkstra.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.kopylchak.dijkstra.exceptions.ExceptionHandler;
import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.algorithms.Dijkstra;
import com.kopylchak.dijkstra.graph.io.*;
import com.kopylchak.dijkstra.graph.productivity.DijkstraProductivityTest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.IntPredicate;

@Singleton
public class ConsoleController implements Controller {
    private ExceptionHandler exceptionHandler;
    private Scanner scanner;
    private GraphReader graphReader;
    private GraphPrinter graphPrinter;
    private DijkstraProductivityTest dijkstraTest;

    private Graph currentGraph;
    private Dijkstra dijkstra;
    private Dijkstra parallelDijkstra;

    @Inject
    public ConsoleController(
        ExceptionHandler exceptionHandler,
        GraphReader graphReader,
        GraphPrinter graphPrinter,
        DijkstraProductivityTest dijkstraTest,
        @Named("dijkstraImpl") Dijkstra dijkstra,
        @Named("parallelDijkstra") Dijkstra parallelDijkstra
    ) {
        scanner = new Scanner(System.in);

        this.exceptionHandler = exceptionHandler;
        this.graphReader = graphReader;
        this.graphPrinter = graphPrinter;
        this.dijkstraTest = dijkstraTest;
        this.dijkstra = dijkstra;
        this.parallelDijkstra = parallelDijkstra;
    }

    @Override
    public void start() {
        while (true) {
            try {
                mainMenu();
            } catch (Exception t) {
                exceptionHandler.handle(t);
            }
        }
    }

    public void mainMenu() {
        System.out.println(
            "1. Read com.kopylchak.dijkstra.graph from file" +
                "\n2. Print current com.kopylchak.dijkstra.graph" +
                "\n3. Calculate the best way" +
                "\n4. Performance test" +
                "\n5. Exit");

        switch (scanInt()) {
            case 1:
                readGraphFromFile();
                break;
            case 2:
                printCurrentGraph();
                break;
            case 3:
                calculateTheBestWay();
                break;
            case 4:
                performanceTest();
                break;
            case 5:
                exit();
        }
    }

    private void readGraphFromFile() {
        String answer;
        System.out.print("Enter file path (or 'back' to return to main menu) -> ");

        while (!(answer = scanString()).equals("back")) {
            try {
                currentGraph = graphReader.readGraph(new FileInputStream(answer));
                break;
            } catch (FileNotFoundException e) {
                System.err.println("Wrong file path");
            }
            System.out.print("Enter file path (or 'back' to return to main menu) -> ");
        }
    }

    private void printCurrentGraph() {
        graphPrinter.printGraph(currentGraph);
    }

    private void calculateTheBestWay() {
        String fromVertex = scanVertexName("Enter start vertex name -> ");
        String endVertex = scanVertexName("Enter end vertex name -> ");

        graphPrinter.printVertexChain(dijkstra.findShortestPath(currentGraph, fromVertex, endVertex));
    }

    private void performanceTest() {
        System.out.println("1. Use current com.kopylchak.dijkstra.graph");
        System.out.println("2. Use generated graphs");
        System.out.println("3. Back");

        int answer = scanInt(i -> i > 0 && i < 4, "Wrong input");

        long sequentialTime = 0;
        long parallelTime = 0;

        System.out.println("Enter thread count -> ");
        int threadCount = scanInt(i -> i > 0, "Wrong input");

        switch (answer) {
            case 1:
                sequentialTime = dijkstraTest.test(currentGraph, dijkstra).toMillis();
                parallelTime = dijkstraTest.testParallel(currentGraph, parallelDijkstra, threadCount).toMillis();
                break;
            case 2:
                System.out.println("Enter com.kopylchak.dijkstra.graph count -> ");
                int graphCount = scanInt(i -> i > 0, "Wrong input");

                System.out.println("Enter vertex number -> ");
                long vertexNumber = scanInt(i -> i > 0, "Wrong input");

                dijkstraTest.generateGraphs(vertexNumber, graphCount);

                sequentialTime = dijkstraTest.test(dijkstra).toMillis();
                parallelTime = dijkstraTest.testParallel(dijkstra, threadCount).toMillis();
                break;
            case 3:
                return;
            default:
                System.err.println("Wrong input");
        }

        printTestResults(parallelTime, sequentialTime);
    }

    private void printTestResults(long parallelTime, long sequentialTime) {
        System.out.println("Time of the sequential algorithm -> " + sequentialTime);
        System.out.println("Time of the parallel algorithm -> " + parallelTime);
        System.out.println("\n");
    }

    private void exit() {
        System.exit(0);
    }

    private int scanInt() {
        return scanner.nextInt();
    }

    private int scanInt(IntPredicate condition, String errorMessage) {
        int result;

        while (!condition.test(result = scanner.nextInt())) {
            System.err.println(errorMessage);
        }

        return result;
    }

    private String scanString() {
        return scanner.next();
    }

    private String scanVertexName(String message) {
        String vertex;

        System.out.println(message);
        while (currentGraph.getVertex(vertex = scanString()) == null) {
            System.err.println("Such vertex doesn't exist");
            System.out.println(message);
        }

        return vertex;
    }
}
