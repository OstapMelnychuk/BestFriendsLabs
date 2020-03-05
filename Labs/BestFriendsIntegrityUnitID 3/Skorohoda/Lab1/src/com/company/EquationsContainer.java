package com.company;

import java.util.ArrayList;

public class EquationsContainer {
    private static ArrayList<Equation> equations = new ArrayList<>();

    public static ArrayList<Equation> getEquations() {
        return equations;
    }

    public static void setEquations(ArrayList<Equation> equations) {
        EquationsContainer.equations = equations;
    }
}
