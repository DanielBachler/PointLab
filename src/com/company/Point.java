package com.company;

public class Point {
    float x;
    float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void printMe() {
        System.out.printf("X-coord: %5.2f Y-coord: %5.2f\n", this.x, this.y);
    }
}
