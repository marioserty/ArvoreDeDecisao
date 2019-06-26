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

    private ArrayList[] trainIndexes;
    private ArrayList[] validIndexes;
    private final int nFolds;

    public KFold(int k) {
        nFolds = k;
        trainIndexes = new ArrayList[nFolds];
        validIndexes = new ArrayList[nFolds];
        for (int i = 0; i < nFolds; i++) {
            trainIndexes[i] = new ArrayList();
            validIndexes[i] = new ArrayList();
        }
    }

    public void split() {
        for (int i = 0; i < Data.numRows; i++) {
            validIndexes[i % nFolds].add(i);
        }
    }

    public ArrayList[] getTrainIndexes() {
        return trainIndexes;
    }

    public ArrayList[] getValidIndexes() {
        return validIndexes;
    }

    public int getnFolds() {
        return nFolds;
    }

}
