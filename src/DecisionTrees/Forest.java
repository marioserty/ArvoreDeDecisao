/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Arithmetic.ArithmeticExpression;
import CrossValidation.KFold;
import Data.Data;
import Metrics.Metrics;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author mario
 */
public class Forest extends DecisionTree {

    private RegressionTree[][] forest;
    private int nFolds;
    public double[] preds;
    private Set<String> columnsUsed = new LinkedHashSet<>();
    private int forestSize;

    public void crossValidate(int iterations, int forestSize, int seed, Metrics metric, int k, double featureFrac) throws InterruptedException {
        
        this.forestSize = forestSize;
        this.nFolds = k;
        forest = new RegressionTree[forestSize][nFolds];
        KFold kfold = new KFold(nFolds);
        kfold.split();
        double forestAverage = 0.0;
        double foldsAverage = 0.0;
        
        System.out.println("Initializing forest...");
        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                forest[i][j] = new RegressionTree(iterations, (seed + i), metric, featureFrac);
                forest[i][j].setValSets(kfold.getTrainIndexes()[j], kfold.getValidIndexes()[j]);
            }
        }

        System.out.println("Training...");
        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                forest[i][j].start();
            }
            for (int j = 0; j < nFolds; j++) {
                forest[i][j].join();
                foldsAverage += forest[i][j].getResult();
            }
            forestAverage += foldsAverage / nFolds;
            System.out.println(
                    i + 1
                    + "\t valid-" + metric.getName() + ": " + String.format("%.05f", foldsAverage / nFolds)
                    + "\t forest-" + metric.getName() + ": " + String.format("%.05f", forestAverage / (i + 1))
                    + "\t seed: " + (seed + i));
            foldsAverage = 0.0;
        }
    }

    public void predict() {
        preds = new double[Data.test.length];
        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                System.out.println(i + "," + j + ": " + forest[i][j].getBestExp());
                double[] foldsPreds = forest[i][j].predict();
                for (int k = 0; k < preds.length; k++) {
                    preds[k] += foldsPreds[k];
                }
            }
        }
        for (int i = 0; i < preds.length; i++) {

            preds[i] = preds[i] / (nFolds * forestSize);

            // For Integer predictions
//            System.out.println(preds[i]);
//            if (preds[i] < .5) {
//                preds[i] = 0;
//            } else {
//                preds[i] = 1;
//            }
        }
    }

    public void isAllColsUsed(ArrayList<Integer> e) {
        for (int i = 0; i < e.size(); i++) {
            columnsUsed.add(Data.columns[e.get(i)]);
        }
        if (columnsUsed.contains(Data.targetCol)) {
            System.err.println("LEAKAGE FOUND! Using target variable at training!");
            System.exit(1);
        }
    }

    public String printTotalColsUsed() {
        return (" total used columns by now: " + columnsUsed.size());
    }

}
