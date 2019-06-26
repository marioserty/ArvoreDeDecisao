/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

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

    public static double[][] data;
    public static int[] target;
    public static int numRows;
    public static int numCols;
    public static String[] columns;

    public static void Read(String filePath, int nRows, int nCols, String del, boolean train) throws IOException {
        numRows = nRows;
        numCols = nCols;
        data = new double[numRows][numCols - 1];
        target = new int[numRows];
        BufferedReader buffer = new BufferedReader(new FileReader(new File(filePath)));
        columns = buffer.readLine().split(del);

        for (int i = 0; buffer.ready(); i++) {

            String line = buffer.readLine();
            String[] values = line.split(del);
            for (int j = 0; j < values.length - 1; j++) {
                data[i][j] = Double.valueOf(values[j]);
            }
            if (train) {
                target[i] = Integer.valueOf(values[values.length - 1]);
            }
        }
    }

    public static void CustomRead(String filePath, int nRows, int nCols, String del, boolean train) throws IOException {
        numRows = nRows;
        numCols = nCols;
        data = new double[numRows][numCols - 1];
        target = new int[numRows];
        BufferedReader buffer = new BufferedReader(new FileReader(new File(filePath)));
        columns = buffer.readLine().split(del);

        for (int i = 0; buffer.ready(); i++) {

            String line = buffer.readLine();
            String[] values = line.split(del);
            for (int j = 0; j < values.length - 1; j++) {
                if (values[j].equals("True")) {
                    data[i][j] = 1;
                } else if (values[j].equals("False")) {
                    data[i][j] = 0;
                } else if("".equals(values[j])){
                    data[i][j] = 0.0;
                }else{
                    data[i][j] = Double.valueOf(values[j]);
                }
            }
            if (train) {
                target[i] = Integer.valueOf(values[values.length - 1]);
            }
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
