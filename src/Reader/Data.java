/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Mário
 */
public class Data {

    public static double[][] train;
    public static double[][] test;
    public static int[] target;
    public static int trainNumRows;
    public static int trainNumCols;
    public static int testNumRows;
    public static int testNumCols;
    public static String[] columns;

    public static void readTrain(String filePath, int nRows, int nCols, String del) throws IOException {
        trainNumRows = nRows;
        trainNumCols = nCols;
        train = new double[trainNumRows][trainNumCols - 1];
        target = new int[trainNumRows];
        BufferedReader buffer = new BufferedReader(new FileReader(new File(filePath)));
        columns = buffer.readLine().split(del);

        for (int i = 0; buffer.ready(); i++) {

            String line = buffer.readLine();
            String[] values = line.split(del);
            for (int j = 0; j < values.length - 1; j++) {
                train[i][j] = Double.valueOf(values[j]);
            }
            target[i] = Integer.valueOf(values[values.length - 1]);
        }
    }

    public static void readTest(String filePath, int nRows, int nCols, String del) throws IOException {
        testNumRows = nRows;
        testNumCols = nCols;
        test = new double[testNumRows][testNumCols];
        BufferedReader buffer = new BufferedReader(new FileReader(new File(filePath)));
        buffer.readLine();

        for (int i = 0; buffer.ready(); i++) {

            String line = buffer.readLine();
            String[] values = line.split(del);
            for (int j = 0; j < values.length; j++) {
                test[i][j] = Double.valueOf(values[j]);
            }
        }
    }

    public static void columnsNames() {
        System.out.print("Colnames: ");
        for (int i = 0; i < Data.columns.length; i++) {
            System.out.print(Data.columns[i] + ", ");
        }
        System.out.println("Total: " + Data.columns.length);
    }
}
