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
import java.util.Random;

/**
 *
 * @author mario
 */
public class Forest extends DecisionTree {

    private final ArrayList<Double> foldsResults;
    private final ArrayList<Double> forestResults;
    private final ArrayList<RegressionTree[]> forest;
    private final int forestSize;
    private double[] predictions;
    private int nFolds;
    public double[] preds;

    public Forest(int forestSize) {
        this.forestSize = forestSize;
        foldsResults = new ArrayList<>();
        forestResults = new ArrayList<>();
        forest = new ArrayList<>();
        preds = new double[Data.test.length];
    }

    public void crossValidate(int iterations, int verboseEval, int forestSize, int seed, Metrics metric, int nFolds, double featureFrac) throws InterruptedException {

        this.nFolds = nFolds;
        RegressionTree[] foldsForest = new RegressionTree[nFolds];
        KFold kfold = new KFold(nFolds);
        kfold.split();
        double sum = 0.0;

        System.out.println("iteration \t");
        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                foldsForest[j] = new RegressionTree(iterations, verboseEval, (seed + i), metric, featureFrac);
                foldsForest[j].setValSets(kfold.getTrainIndexes()[j], kfold.getValidIndexes()[j]);
                foldsForest[j].start();
            }
            for (int j = 0; j < nFolds; j++) {
                foldsForest[j].join();
                foldsResults.add(foldsForest[j].getResult());
            }
            forest.add(foldsForest);
            sum += getFoldsMean();
            System.out.println(i + "\t" + "valid-" + metric.getName() + ": " + String.format("%.05f", getFoldsMean())
                    + "\t\t forest-" + metric.getName() + ": " + String.format("%.05f", sum / (i + 1))
                    + "\t\tseed: " + (seed + i));
            foldsResults.clear();
        }
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
//            System.out.println(preds[i]);
            if (preds[i] < .5) {
                preds[i] = 0;
            } else {
                preds[i] = 1;
            }
        }
    }

    private double getFoldsMean() {
        double mean = 0.0;
        for (int i = 0; i < foldsResults.size(); i++) {
            mean += foldsResults.get(i);
        }
        return mean / foldsResults.size();
    }

    public double getForestMean() {
        double mean = 0.0;
        for (Double d : forestResults) {
            mean += d;
        }
        return mean / forestResults.size();
    }

    public double[] predict() {
        predictions = new double[Data.test.length];

        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                for (int k = 0; k < predictions.length; k++) {
                    predictions[k] += 1.0 / (1.0 + Math.exp(-forest.get(i)[j].getBestExp().processOnTest(k)));
                }
            }
        }
        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = predictions[i] / (nFolds * forestSize);
        }
        return predictions;
    }

}
