/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
    public static int numRows;
    public static int numCols;
    public static String[] columns;

    public static void ReadTrain(String filePath, int nRows, int nCols, String del) throws IOException {
        numRows = nRows;
        numCols = nCols - 1;
        train = new double[numRows][numCols];
        target = new int[numRows];
        BufferedReader buffer = new BufferedReader(new FileReader(new File(filePath)));
        columns = buffer.readLine().split(del);

        for (int i = 0; i < numRows; i++) {

            String line = buffer.readLine();
            String[] values = line.split(del);
            for (int j = 0; j < numCols; j++) {
                train[i][j] = Double.valueOf(values[j]);
            }
            target[i] = Integer.valueOf(values[values.length - 1]);
        }
    }

    public static void ReadTest(String filePath, int nRows, int nCols, String del) throws IOException {;
        BufferedReader buffer = new BufferedReader(new FileReader(new File(filePath)));
        buffer.readLine();
        test = new double[nRows][nCols];

        for (int i = 0; i < nRows; i++) {
            String line = buffer.readLine();
            String[] values = line.split(del);
            for (int j = 0; j < nCols; j++) {
                test[i][j] = Double.valueOf(values[j]);
            }
        }
    }

    public static void WritePredictions(String filePath, String exampleFilePath, double[] preds) throws FileNotFoundException, IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(new File(exampleFilePath)));
        String[] subCols = buffer.readLine().split(",");
        String ids[] = new String[preds.length];

        for (int i = 0; buffer.ready(); i++) {
            String line = buffer.readLine();
            String[] values = line.split(",");
            ids[i] = values[0];
        }

        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(filePath));
        PrintWriter pr = new PrintWriter(writer);
        pr.println(subCols[0] + "," + subCols[1]);
        for (int i = 0; i < preds.length; i++) {
            pr.println(ids[i] + "," + preds[i]);
        }
        pr.close();
        writer.close();
        
    }

    public static void CustomRead(String filePath, int nRows, int nCols, String del) throws IOException {
        numRows = nRows;
        numCols = nCols - 1;
        train = new double[numRows][numCols];
        target = new int[numRows];
        BufferedReader buffer = new BufferedReader(new FileReader(new File(filePath)));
        columns = buffer.readLine().split(del);

        for (int i = 0; i < numRows; i++) {
            String line = buffer.readLine();
            String[] values = line.split(del);
            for (int j = 0; j < numCols; j++) {
                if (values[j].equals("True")) {
                    train[i][j] = 1;
                } else if (values[j].equals("False")) {
                    train[i][j] = 0;
                } else if ("".equals(values[j])) {
                    train[i][j] = 0.0;
                } else {
                    train[i][j] = Double.valueOf(values[j]);
                }
            }
            target[i] = Integer.valueOf(values[values.length - 1]);
        }
    }

    public void columnsNames() {
        System.out.print("Colnames: ");
        for (String column : columns) {
            System.out.print(column + ", ");
        }
        System.out.println("Total: " + columns.length);
    }
}
