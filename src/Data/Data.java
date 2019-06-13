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

    public double[][] data;
    public int[] target;
    public int numRows;
    public int numCols;
    public String[] columns;

    public void Read(String filePath, int nRows, int nCols, String del, boolean train) throws IOException {
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

    public Data subset(int[] indexes) {
        Data newData = new Data();
        newData.columns = this.columns;
        newData.numCols = this.numCols;
        newData.numRows = indexes.length;
        newData.target = new int[newData.numRows];
        newData.data = new double[newData.numRows][numCols - 1];

        for (int i = 0; i < indexes.length; i++) {
            for (int j = 0; j < numCols - 1; j++) {
                newData.data[i][j] = this.data[indexes[i]][j];
            }
            newData.target[i] = this.target[indexes[i]];
        }
        return newData;
    }

    public void columnsNames() {
        System.out.print("Colnames: ");
        for (int i = 0; i < columns.length; i++) {
            System.out.print(columns[i] + ", ");
        }
        System.out.println("Total: " + columns.length);
    }
}
