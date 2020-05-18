package com.company;

public class Equation {
    private Integer x;
    private Integer y;

    public Equation(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Equation() {
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

    @Override
    public String toString() {
        return "Equation{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
