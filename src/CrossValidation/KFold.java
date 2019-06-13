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
    private int trainLenght;
    private ArrayList<int[]> trainIndex;
    private ArrayList<int[]> testIndex;
    private Data data;

    public KFold(Data data, int k) {
        this.data = data;
        nFolds = k;
        trainLenght = data.numRows / nFolds;
        trainIndex = new ArrayList<>();
        testIndex = new ArrayList<>();
    }

    public void split() {
        int start = 0;
        int end = data.numRows / nFolds;
        int[] testFold;
        int[] trainFold;

        for (int k = 0; k < nFolds - 1; k++) {
            testFold = new int[trainLenght];
            trainFold = new int[trainLenght];
            for (int i = 0, test = 0, train = 0; i < data.numRows; i++) {
                if (i < end && i > start) {
                    testFold[test] = i;
                    test++;
                } else {
                    trainFold[train] = i;
                    train++;
                }
                start = end;
                end += end;
            }
            trainIndex.add(trainFold);
            testIndex.add(testFold);
        }
        for (int i = 0, test = 0, train = 0; i < data.numRows; i++) {
            if (i < end && i > start) {
                testFold[test] = i;
                test++;
            } else {
                trainFold[train] = i;
                train++;
            }
            start = end;
            end += end;
        }
        trainIndex.add(trainFold);
        testIndex.add(testFold);
        trainFold = new int[trainLenght + data.numRows % nFolds];
        testFold = new int[trainLenght + data.numRows % nFolds];
        trainIndex.add(testFold);
        testIndex.add(testFold);

    }
}
