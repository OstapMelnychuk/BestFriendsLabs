package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CountingThreads implements Callable<Boolean> {
    private ArrayList<Equation> allEquations;
    private List<Equation> partOfEquations;

    public CountingThreads(ArrayList<Equation> allEquations, List<Equation> partOfEquations) {
        this.allEquations = allEquations;
        this.partOfEquations = partOfEquations;
    }

    @Override
    public Boolean call() throws Exception {
        boolean areParallel = false;
        int counter = 0;
        for (int i = 0; i < partOfEquations.size(); i++){
            Integer x1 = partOfEquations.get(i).getX();
            Integer y1 = partOfEquations.get(i).getY();
            for (int j = i + 1; j < allEquations.size() - 1; j++){
                Integer x2 = allEquations.get(j).getX();
                Integer y2 = allEquations.get(j).getY();
                int result = (x1 * y2) - (x2 * y1);
                if (result == 0 && !x1.equals(x2) && !y1.equals(y2)){
                    areParallel = true;
                }
            }
        }
        return areParallel;
    }

    public ArrayList<Equation> getAllEquations() {
        return allEquations;
    }

    public void setAllEquations(ArrayList<Equation> allEquations) {
        this.allEquations = allEquations;
    }

    public List<Equation> getPartOfEquations() {
        return partOfEquations;
    }

    public void setPartOfEquations(List<Equation> partOfEquations) {
        this.partOfEquations = partOfEquations;
    }
}
