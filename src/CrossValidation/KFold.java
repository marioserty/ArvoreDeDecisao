/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrossValidation;

import Arithmetic.ArithmeticExpression;
import Data.Data;
import DecisionTrees.DecisionTree;
import DecisionTrees.RootWiseTree;
import Metrics.AUC;
import Metrics.Metrics;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class KFold implements Validation {

    private final ArrayList[] trainIndexes;
    private final ArrayList[] validIndexes;
    private final int nFolds;
    private final double mean = 0.0;

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
        for (int i = 0; i < nFolds; i++) {
            for (int j = 0; j < Data.numRows; j++) {
                if(!validIndexes[i].contains(j))
                    trainIndexes[i].add(j);
            }
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
