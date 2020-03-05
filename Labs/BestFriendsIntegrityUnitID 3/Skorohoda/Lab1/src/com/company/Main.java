package com.company;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        EquationManager equationManager = new EquationManager(false, 0, "Points");
        Instant start = Instant.now();
        System.out.println(equationManager.areParallelLines());
        Instant end = Instant.now();
        Duration interval = Duration.between(start, end);
        System.out.println("Execution time with iterations is: " + interval.getSeconds() + " seconds.");
        ThreadCreator threadCreator = new ThreadCreator(EquationsContainer.getEquations());
        System.out.println(threadCreator.createAndRunThreads(8));
    }
}
