/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrossValidation;

import Data.Data;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class KFold implements Validation {

    private int nFolds;
    private int currentFold;
    private int testLenght;
    private ArrayList<int[]> trainIndex;
    private ArrayList<int[]> testIndex;
    private Data data;

    public KFold(Data data, int k) {
        this.data = data;
        nFolds = k;
        testLenght = data.numRows / nFolds;
        trainIndex = new ArrayList<>();
        testIndex = new ArrayList<>();
    }

    public void split() {
        int start = 0;
        int end = data.numRows / getnFolds();
        int[] testFold;
        int[] trainFold;

        for (int k = 0; k < getnFolds() - 1; k++) {
            testFold = new int[testLenght];
            trainFold = new int[data.numRows - testLenght];
            for (int i = 0, test = 0, train = 0; i < data.numRows; i++) {
                if ((i < end) && (i >= start)) {
                    testFold[test] = i;
                    test++;
                } else {
                    trainFold[train] = i;
                    train++;
                }
            }
            start = end;
            end += testLenght;
            getTrainIndex().add(trainFold);
            getTestIndex().add(testFold);
        }
        start = end - testLenght;
        end = data.numRows;
        trainFold = new int[data.numRows - (data.numRows % getnFolds())];
        testFold = new int[testLenght + data.numRows % getnFolds() - 1];
        for (int i = 0, test = 0, train = 0; i < data.numRows; i++) {
            if (i < end && i > start) {
                testFold[test] = i;
                test++;
            } else {
                trainFold[train] = i;
                train++;
            }
        }
        getTrainIndex().add(testFold);
        getTestIndex().add(testFold);

    }

    public int getnFolds() {
        return nFolds;
    }

    public int getCurrentFold() {
        return currentFold;
    }

    public ArrayList<int[]> getTrainIndex() {
        return trainIndex;
    }

    public ArrayList<int[]> getTestIndex() {
        return testIndex;
    }
}
