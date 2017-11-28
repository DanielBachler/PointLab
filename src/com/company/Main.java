package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    //The name of the file that contains the input
    public static final String inputFile = "input.txt";
    //An array of points
    public static Point[] points;
    //The x-sorted array
    public static Point[] xSorted;
    //The y-sorted array
    public static Point[] ySorted;

    public static void main(String[] args) {
        fileInput();
        //printArray(points);
        prepToSortArrays();
        sortX(points, 0, points.length, xSorted);
        numberPoints(xSorted);
        //printArray(xSorted);
        //sortY(points, 0, points.length, ySorted);
        //printArray(ySorted);
        double min = recursion(points, Double.MAX_VALUE);
        min = minLR(min);
    }

    //Reads input file and creates the point array
    public static void fileInput() {
        try {
            //Creates the file and scanner for input
            File input = new File(inputFile);
            Scanner inputReader = new Scanner(input);
            //Creates a temp linked list for holding points, and a counter var
            LinkedList<Point> temp = new LinkedList<>();
            int numPoints = 0;
            //Iterates until no points remain in input
            while (inputReader.hasNextLine()) {
                //Gets the next line and creates a tokenizer that breaks on commas
                String line = inputReader.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                //Creates an x and y value string
                String xString = tokenizer.nextToken();
                String yString = tokenizer.nextToken();
                //Parses the x and y doubles from each string
                double x = Double.parseDouble(xString);
                double y = Double.parseDouble(yString);
                //Creates a new point, adds it to the linked list and increments the counter
                Point point = new Point(x, y);
                temp.add(point);
                numPoints++;
            }
            //Points is initialized with the correct size
            points = new Point[numPoints];
            //Moves points from linked list to array
            for (int i = 0; i < numPoints; i++) {
                points[i] = temp.get(i);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    //Prints each point in the point array
    public static void printArray(Point[] printMe) {
        for (Point p : printMe) {
            p.printMe();
        }
        System.out.println();
    }

    //Sets up both x and y arrays to be sorted
    public static void prepToSortArrays() {
        //Initializes x and y
        xSorted = new Point[points.length];
        ySorted = new Point[points.length];
        //Copies all elements of points to x and y arrays
        int i = 0;
        for (Point p : points) {
            xSorted[i] = p;
            ySorted[i++] = p;
        }
    }

    //Sorts the x-array
    public static void sortX(Point[] points, int start, int stop, Point[] xArray) {
        //Checks that run length is more than 1
        if (stop - start < 2) {
            return;
        }
        int middle = (start + stop) / 2;
        sortX(xArray, start, middle, points);
        sortX(xArray, middle, stop, points);

        mergeX(xArray, start, middle, stop, points);
    }

    //Merge sort for X
    public static void mergeX(Point[] xArray, int start, int middle, int stop, Point[] points) {
        int i = start;
        int j = middle;

        for (int k = start; k < stop; k++) {
            if (i < middle && (j >= stop || points[i].x <= points[j].x)) {
                xArray[k] = points[i];
                i++;
            } else {
                xArray[k] = points[j];
                j++;
            }
        }
    }

    //Numbers points in x
    public static void numberPoints(Point[] input) {
        for(int i = 0; i < input.length; i++) {
            input[i].spot = i;
        }
    }

    //Splits y-array up for sorting
    public static void sortY(Point[] points, int start, int stop, Point[] yArray) {
        //Checks that run length is more than 1
        if (stop - start < 2) {
            return;
        }
        int middle = (start + stop) / 2;
        sortY(yArray, start, middle, points);
        sortY(yArray, middle, stop, points);

        mergeY(yArray, start, middle, stop, points);
    }

    //Merge sort for Y
    public static void mergeY(Point[] yArray, int start, int middle, int stop, Point[] points) {
        int i = start;
        int j = middle;

        for (int k = start; k < stop; k++) {
            if (i < middle && (j >= stop || points[i].y <= points[j].y)) {
                yArray[k] = points[i];
                i++;
            } else {
                yArray[k] = points[j];
                j++;
            }
        }
    }

    public static double recursion(Point[] points, double distance) {
        try {
            System.out.printf("Solving problem: Point[%d]...Point[%d]\n", points[0].spot, points[points.length - 1].spot);
        } catch (NullPointerException e) {
            System.out.printf("Solving problem: Point[%d]...Point[%d]\n", points[0].spot, points[0].spot);
        }
        Point[] pL = new Point[(int)Math.ceil((points.length) / 2.0)];
        Point[] pR = new Point[(int)Math.ceil((points.length) / 2.0)];
        double lDist;
        double rDist;
        double minDist = distance;


        for (int i = 0; i < points.length / 2; i++) {
            pL[i] = points[i];
        }
        for (int i = (points.length / 2), j = 0; i < points.length; i++, j++) {
            pR[j] = points[i];
        }
        if (pL.length == 2) {
            double distL;
            double distR;
            //finds distance for pL
            if(pL[0] != null && pL[1] != null) {
                distL = Math.sqrt( Math.pow((pL[0].x - pL[1].x), 2) + Math.pow((pL[0].y - pL[1].y), 2));
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: %.1f\n", pL[0].x, pL[0].y, pL[1].x, pL[1].y, distL);
            } else {
                distL = Double.MAX_VALUE;
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: INF\n", pL[0].x, pL[0].y, pL[0].x, pL[0].y);
            }
            //Finds distance for pR
            if(pR[0] != null && pR[1] != null) {
                distR = Math.sqrt( Math.pow((pR[0].x - pR[1].x), 2) + Math.pow((pR[0].y - pR[1].y), 2));
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: %.1f\n", pR[0].x, pR[0].y, pR[1].x, pR[1].y, distR);
            } else {
                distR = Double.MAX_VALUE;
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: INF\n", pR[0].x, pR[0].y, pR[0].x, pR[0].y);
            }
            //Compares distR, distL and minDist
            double temp = Math.min(distR, distL);
            minDist = Math.min(temp, minDist);
            try {
                System.out.printf("Combining Problems: Point[%d]...Point[%d] and Point[%d]...Point[%d]\n\tFound Result, distance: %.1f\n", pL[0].spot, pL[1].spot, pR[0].spot, pR[1].spot, minDist);
            } catch (NullPointerException e) {
                System.out.printf("Combining Problems with a duplicate\n\tFound Result, Distance: %.1f\n", minDist);
            }
            return minDist;
        } else {
            System.out.printf("\tDividing at Point[%d]\n", (int)Math.ceil((points.length) / 2.0));
            minDist = recursion(pL, minDist);
            minDist = recursion(pR, minDist);
            return minDist;
        }
    }

    public static double minLR(double minDist) {
        double temp = Double.MAX_VALUE;
        for(int i = 0, j = points.length; i == points.length / 2; i++, j--) {
            temp = Math.min(Math.sqrt(Math.pow(xSorted[i].x - xSorted[j].x, 2) + Math.pow(xSorted[i].y - xSorted[j].y, 2)), temp);
        }
        double min = Math.min(temp, minDist);
        System.out.printf("Combining recursive min and pair mins\nResult: %.1f", min);
        return min;
    }
}
