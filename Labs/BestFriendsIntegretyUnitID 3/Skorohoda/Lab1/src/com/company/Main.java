package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Equation equation = new Equation();
        //equation.generatePoints(100000, "Points");
        equation.getPointsFromFile("Points");
        Instant start = Instant.now();
        System.out.println(equation.areParallelLines());
        Instant end = Instant.now();
        Duration interval = Duration.between(start, end);
        System.out.println("Execution time is: " + interval.getSeconds() + " seconds.");

        List<Equation> partOfEquations1 = equation.getEquations().subList(0, equation.getEquations().size() / 8);
        List<Equation> partOfEquations2 = equation.getEquations().subList(equation.getEquations().size() / 8, (equation.getEquations().size() / 8) * 2);
        List<Equation> partOfEquations3 = equation.getEquations().subList((equation.getEquations().size() / 8) * 2, (equation.getEquations().size() / 8) * 3);
        List<Equation> partOfEquations4 = equation.getEquations().subList((equation.getEquations().size() / 8) * 3, (equation.getEquations().size() / 8) * 4);
        List<Equation> partOfEquations5 = equation.getEquations().subList((equation.getEquations().size() / 8) * 4, (equation.getEquations().size() / 8) * 5);
        List<Equation> partOfEquations6 = equation.getEquations().subList((equation.getEquations().size() / 8) * 5, (equation.getEquations().size() / 8) * 6);
        List<Equation> partOfEquations7 = equation.getEquations().subList((equation.getEquations().size() / 8) * 6, (equation.getEquations().size() / 8) * 7);
        List<Equation> partOfEquations8 = equation.getEquations().subList((equation.getEquations().size() / 8) * 7, equation.getEquations().size());

//        List<Equation> partOfEquations1 = equation.getEquations().subList(0, equation.getEquations().size() / 2);
//        List<Equation> partOfEquations2 = equation.getEquations().subList(equation.getEquations().size() / 2, equation.getEquations().size());

        System.out.println(partOfEquations1.size());
        System.out.println(partOfEquations2.size());
        System.out.println(partOfEquations3.size());
        System.out.println(partOfEquations4.size());
        System.out.println(partOfEquations5.size());
        System.out.println(partOfEquations6.size());
        System.out.println(partOfEquations7.size());
        System.out.println(partOfEquations8.size());

        CountingThreads countingThread1 = new CountingThreads(equation.getEquations(), partOfEquations1);
        CountingThreads countingThread2 = new CountingThreads(equation.getEquations(), partOfEquations2);
        CountingThreads countingThread3 = new CountingThreads(equation.getEquations(), partOfEquations3);
        CountingThreads countingThread4 = new CountingThreads(equation.getEquations(), partOfEquations4);
        CountingThreads countingThread5 = new CountingThreads(equation.getEquations(), partOfEquations5);
        CountingThreads countingThread6 = new CountingThreads(equation.getEquations(), partOfEquations6);
        CountingThreads countingThread7 = new CountingThreads(equation.getEquations(), partOfEquations7);
        CountingThreads countingThread8 = new CountingThreads(equation.getEquations(), partOfEquations8);

//        FutureTask<Boolean> futureTask1 = new FutureTask<>(countingThread1);
//        FutureTask<Boolean> futureTask2 = new FutureTask<>(countingThread2);
//        FutureTask<Boolean> futureTask3 = new FutureTask<>(countingThread3);
//        FutureTask<Boolean> futureTask4 = new FutureTask<>(countingThread4);
//
//        Instant startThread = Instant.now();
//        new Thread(futureTask1).start();
//        new Thread(futureTask2).start();
//        new Thread(futureTask3).start();
//        new Thread(futureTask4).start();
//
//        try {
//            if (futureTask1.get() || futureTask2.get() || futureTask3.get() || futureTask4.get()){
//                System.out.println("There are parallel lines");
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        Instant endThread  = Instant.now();
//        Duration intervalThread = Duration.between(startThread, endThread);
//        System.out.println("Execution time is: " + intervalThread.getSeconds() + " seconds.");

        List<Callable<Boolean>> workers = new ArrayList<>();
        workers.add(countingThread1);
        workers.add(countingThread2);
        workers.add(countingThread3);
        workers.add(countingThread4);
        workers.add(countingThread5);
        workers.add(countingThread6);
        workers.add(countingThread7);
        workers.add(countingThread8);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Instant startThread = Instant.now();

        try {
            List<Future<Boolean>> results = service.invokeAll(workers);
            for (Future<Boolean> result : results) {
                if (result.get().equals(true)) {
                    System.out.println("There are parallel lines");
                    break;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
        Instant endThread = Instant.now();
        Duration intervalThread = Duration.between(startThread, endThread);
        System.out.println("Execution time is: " + intervalThread.getSeconds() + " seconds.");
    }
}
