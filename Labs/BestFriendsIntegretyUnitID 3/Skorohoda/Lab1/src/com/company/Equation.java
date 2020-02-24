package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Equation {
    private Integer x;
    private Integer y;
    private ArrayList<Equation> equations = new ArrayList<>();

    public Equation(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Equation() {
    }

    public void getPointsFromFile(String fileNameWithoutFormat) {
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

    public void generatePoints(Integer quantity, String fileNameWithoutFormat) {
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

    public boolean areParallelLines(){
        boolean areParallel = false;
        int counter = 0;
        for (int i = 0; i < equations.size(); i++){
            Integer x1 = equations.get(i).x;
            Integer y1 = equations.get(i).y;
            for (int j = i + 1; j < equations.size() - 1; j++){
                Integer x2 = equations.get(j).x;
                Integer y2 = equations.get(j).y;
                int result = (x1 * y2) - (x2 * y1);
                if (result == 0 && !x1.equals(x2) && !y1.equals(y2)){
//                    System.out.println("Parallel lines with indexes: " +  i + " " + j);
//                    System.out.println("----------------------------------------------");
//                    System.out.println(equations.get(i));
//                    System.out.println(equations.get(j));
                    areParallel = true;
                }
            }
        }
        return areParallel;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public ArrayList<Equation> getEquations() {
        return equations;
    }

    public void setEquations(ArrayList<Equation> equations) {
        this.equations = equations;
    }

    @Override
    public String toString() {
        return "Equation{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
