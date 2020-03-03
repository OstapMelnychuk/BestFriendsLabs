package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class EquationManager {
    private ArrayList<Equation> equations = EquationsContainer.getEquations();

    public EquationManager(boolean generatePoints, int quantity, String fileNameWithoutFormat){
        if (generatePoints){
            generatePoints(quantity, fileNameWithoutFormat);
        }
        getEquationsFromFile(fileNameWithoutFormat);
    }

    public void getEquationsFromFile(String fileNameWithoutFormat) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileNameWithoutFormat + ".txt")))) {
            String x;
            String y;
            while ((x = bufferedReader.readLine()) != null) {
                if ((y = bufferedReader.readLine()) != null) {
                    equations.add(new Equation(Integer.parseInt(x), Integer.parseInt(y)));
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generatePoints(Integer quantity, String fileNameWithoutFormat) {
        File file = new File(fileNameWithoutFormat + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            Random random = new Random();
            for (int i = 0; i < quantity; i++) {
                bufferedWriter.write(String.valueOf(random.nextInt(10000)));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean areParallelLines() {
        boolean areParallel = false;
        for (int i = 0; i < equations.size(); i++) {
            Integer x1 = equations.get(i).getX();
            Integer y1 = equations.get(i).getY();
            for (int j = i + 1; j < equations.size() - 1; j++) {
                Integer x2 = equations.get(j).getX();
                Integer y2 = equations.get(j).getY();
                int result = (x1 * y2) - (x2 * y1);
                if (result == 0 && !x1.equals(x2) && !y1.equals(y2)) {
                    areParallel = true;
                }
            }
        }
        return areParallel;
    }
}
