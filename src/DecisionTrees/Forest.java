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

    private ArrayList<Double> forestResults;
    private ArrayList<RegressionTree[]> forest;
    private int forestSize;
    private int nFolds;
    public double[] preds;
    private double foldsResults;
    private Set<String> columnsUsed = new LinkedHashSet<>();

    public Forest(int forestSize) {
        this.forestSize = forestSize;
        forestResults = new ArrayList<>();
        forest = new ArrayList<>();
    }

    public void crossValidate(int iterations, int forestSize, int seed, Metrics metric, int nFolds, double featureFrac) throws InterruptedException {

        System.out.println("Training...");
        
        this.nFolds = nFolds;
        RegressionTree[] foldsForest = new RegressionTree[nFolds];
        KFold kfold = new KFold(nFolds);
        kfold.split();
        double forestAverage = 0.0;
        double foldsAverage = 0.0;

//        System.out.println("iteration \t");
        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                foldsForest[j] = new RegressionTree(iterations, (seed + i), metric, featureFrac);
                foldsForest[j].setValSets(kfold.getTrainIndexes()[j], kfold.getValidIndexes()[j]);
                foldsForest[j].start();
            }
            for (int j = 0; j < nFolds; j++) {
                foldsForest[j].join();
                foldsAverage += foldsForest[j].getResult();
//                isAllColsUsed(foldsForest[j].getTrainCols());
            }
            forest.add(foldsForest);
            forestAverage += foldsAverage / nFolds;
            System.out.println(
                    i + 1
                    + "\t" + "valid-" + metric.getName() + ": " + String.format("%.05f", foldsAverage / nFolds)
                    + "\t forest-" + metric.getName() + ": " + String.format("%.05f", forestAverage / (i + 1))
                    + "\t seed: " + (seed + i));
//                    + "\t " + printTotalColsUsed());
            foldsAverage = 0.0;
        }
    }

//    private double getFoldsMean() {
//        double mean = 0.0;
//        for (int i = 0; i < foldsResults.size(); i++) {
//            mean += foldsResults.get(i);
//        }
//        return mean / foldsResults.size();
//    }

    public double getForestMean() {
        double mean = 0.0;
        for (Double d : forestResults) {
            mean += d;
        }
        return mean / forestResults.size();
    }

    public void predict() {
        preds = new double[Data.test.length];
        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                double[] foldsPreds = forest.get(i)[j].predict();
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
