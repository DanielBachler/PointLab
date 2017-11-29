package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    //The name of the file that contains the input
    private static final String inputFile = "input.txt";
    //An array of points
    private static Point[] points;
    //The x-sorted array
    private static Point[] xSorted;
    //The y-sorted array
    //private static Point[] ySorted;

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
        minLR(min);
    }

    //Reads input file and creates the point array
    private static void fileInput() {
        try {
            //Creates the file and scanner for input
            File input = new File(inputFile);
            Scanner inputReader = new Scanner(input);
            //Creates a temp linked list for holding points, and a counter var
            LinkedList<Point> temp = new LinkedList<>(); // creates linked list of points
            int numPoints = 0; // starts as 0
            //Iterates until no points remain in input
            while (inputReader.hasNextLine()) { // loops that goes through lines
                //Gets the next line and creates a tokenizer that breaks on commas
                String line = inputReader.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, ","); // breaks at commas
                //Creates an x and y value string
                String xString = tokenizer.nextToken();
                String yString = tokenizer.nextToken();
                //Parses the x and y doubles from each string
                double x = Double.parseDouble(xString); // parses string to double
                double y = Double.parseDouble(yString);
                //Creates a new point, adds it to the linked list and increments the counter
                Point point = new Point(x, y); // send to point class
                temp.add(point);
                numPoints++;
            }
            //Points is initialized with the correct size
            points = new Point[numPoints];
            //Moves points from linked list to array
            for (int i = 0; i < numPoints; i++) {
                points[i] = temp.get(i);
            }
        } catch (FileNotFoundException e) { // makes sure files received
            System.out.println("File not found");
        }
    }

    //Prints each point in the given array
    public static void printArray(Point[] printMe) { // simple print method
        for (Point p : printMe) {
            p.printMe();
        }
        System.out.println();
    }

    //Sets up both x and y arrays to be sorted
    private static void prepToSortArrays() {
        //Initializes x and y
        xSorted = new Point[points.length];
        //ySorted = new Point[points.length];
        //Copies all elements of points to x and y arrays
        int i = 0;
        for (Point p : points) {
            xSorted[i++] = p;
            //If the ySorted is uncommented you need to remove i++ from xSorted and replace it with i
            //ySorted[i++] = p;
        }
    }

    //Sorts the x-array
    private static void sortX(Point[] points, int start, int stop, Point[] xArray) {
        //Checks that run length is more than 1
        if (stop - start < 2) {
            return;
        }
        int middle = (start + stop) / 2;
        sortX(xArray, start, middle, points); //recursive calls to sort by x values
        sortX(xArray, middle, stop, points);
        //Merges halved arrays
        mergeX(xArray, start, middle, stop, points);
    }

    //Merge sort for X
    private static void mergeX(Point[] xArray, int start, int middle, int stop, Point[] points) {
        int i = start;
        int j = middle;

        for (int k = start; k < stop; k++) {
            if (i < middle && (j >= stop || points[i].x <= points[j].x)) { //compares iterators with the given middle and stop
                xArray[k] = points[i];
                i++;
            } else {
                xArray[k] = points[j];
                j++;
            }
        }
    }

    //Numbers points in x
    private static void numberPoints(Point[] input) {
        for(int i = 0; i < input.length; i++) {
            input[i].spot = i;
        }
    }

    //Splits y-array up for sorting
    private static void sortY(Point[] points, int start, int stop, Point[] yArray) {
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
    private static void mergeY(Point[] yArray, int start, int middle, int stop, Point[] points) {
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

    //Finds smallest value
    private static double recursion(Point[] points, double distance) {
        //Prints which problem is being solved.  Due to some spots in the array being null try catch is needed
        try {
            System.out.printf("Solving problem: Point[%d]...Point[%d]\n", points[0].spot, points[points.length - 1].spot);
        } catch (NullPointerException e) {
            System.out.printf("Solving problem: Point[%d]...Point[%d]\n", points[0].spot, points[0].spot);
        }
        Point[] pL = new Point[(int)Math.ceil((points.length) / 2.0)]; //splits array into half
        Point[] pR = new Point[(int)Math.ceil((points.length) / 2.0)];
        double minDist = distance;


        for (int i = 0; i < points.length / 2; i++) { //gives value to pL
            pL[i] = points[i];
        }
        for (int i = (points.length / 2), j = 0; i < points.length; i++, j++) { //gives values to pR
            pR[j] = points[i];
        }
        if (pL.length == 2) { //if the arrays are down to size two then we calculate distances
            double distL;
            double distR;
            //finds distance for pL
            if(pL[0] != null && pL[1] != null) { //if no null value then it calculates and prints step
                distL = Math.sqrt( Math.pow((pL[0].x - pL[1].x), 2) + Math.pow((pL[0].y - pL[1].y), 2));
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: %.1f\n", pL[0].x, pL[0].y, pL[1].x, pL[1].y, distL);
            } else { //if there is null it sets it to max
                distL = Double.MAX_VALUE;
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: INF\n", pL[0].x, pL[0].y, pL[0].x, pL[0].y);
            }
            //Finds distance for pR
            if(pR[0] != null && pR[1] != null) { //if no null value then it calculates and prints step
                distR = Math.sqrt( Math.pow((pR[0].x - pR[1].x), 2) + Math.pow((pR[0].y - pR[1].y), 2));
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: %.1f\n", pR[0].x, pR[0].y, pR[1].x, pR[1].y, distR);
            } else { //if there is null it sets it to max
                distR = Double.MAX_VALUE;
                System.out.printf("\tFound Result: P1: (%.1f, %.1f), P2: (%.1f, %.1f), Distance: INF\n", pR[0].x, pR[0].y, pR[0].x, pR[0].y);
            }
            //Compares distR, distL and minDist
            double temp = Math.min(distR, distL); // finds minimum out of two
            minDist = Math.min(temp, minDist); // checks if found min is smaller than current min
            try { //tests for null pointer exception
                System.out.printf("Combining Problems: Point[%d]...Point[%d] and Point[%d]...Point[%d]\n\tFound Result, distance: %.1f\n", pL[0].spot, pL[1].spot, pR[0].spot, pR[1].spot, minDist);
            } catch (NullPointerException e) {
                System.out.printf("Combining Problems with a duplicate\n\tFound Result, Distance: %.1f\n", minDist);
            }
            return minDist;
        } else { // if array is more than 2 it calls the method again
            System.out.printf("\tDividing at Point[%d]\n", (int)Math.ceil((points.length) / 2.0)); // step print
            minDist = recursion(pL, minDist); // left side first
            minDist = recursion(pR, minDist); // then right side
            return minDist; // gives min distance found
        }
    }

    private static double minLR(double minDist) {
        double temp = Double.MAX_VALUE;
        for(int i = 0, j = points.length; i == points.length / 2; i++, j--) {
            temp = Math.min(Math.sqrt(Math.pow(xSorted[i].x - xSorted[j].x, 2) + Math.pow(xSorted[i].y - xSorted[j].y, 2)), temp);
        }
        double min = Math.min(temp, minDist);
        System.out.printf("Combining recursive min and pair min\nResult: %.1f", min);
        return min;
    }
}
