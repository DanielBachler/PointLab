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
        printArray();
        prepToSortArrays();
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
            while(inputReader.hasNextLine()) {
                //Gets the next line and creates a tokenizer that breaks on commas
                String line = inputReader.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                //Creates an x and y value string
                String xString = tokenizer.nextToken();
                String yString = tokenizer.nextToken();
                //Parses the x and y floats from each string
                float x = Float.parseFloat(xString);
                float y = Float.parseFloat(yString);
                //Creates a new point, adds it to the linked list and increments the counter
                Point point = new Point(x, y);
                temp.add(point);
                numPoints++;
            }
            //Points is initialized with the correct size
            points = new Point[numPoints];
            //Moves points from linked list to array
            for(int i = 0; i < numPoints; i++) {
                points[i] = temp.get(i);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    //Prints each point in the point array
    public static void printArray() {
        for(Point p: points) {
            p.printMe();
        }
    }

    //Sets up both x and y arrays to be sorted
    public static void prepToSortArrays() {
        //Initializes x and y
        xSorted = new Point[points.length];
        ySorted = new Point[points.length];
        //Copies all elements of points to x and y arrays
        int i = 0;
        for(Point p: points) {
            xSorted[i] = p;
            ySorted[i++] = p;
        }
    }

    //Sorts the x-array
    public static void sortX() {

    }

    //Sorts the y-array
    public static void sortY() {

    }
}
