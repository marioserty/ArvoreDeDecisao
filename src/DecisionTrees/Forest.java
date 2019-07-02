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
    private final ArrayList<RootWiseTree[]> forest;
    private final int forestSize;
    private double[] predictions;
    private int nFolds;

    public Forest(int forestSize) {
        this.forestSize = forestSize;
        foldsResults = new ArrayList<>();
        forestResults = new ArrayList<>();
        forest = new ArrayList<>();
    }

    public void crossValidate(int iterations, int verboseEval, int forestSize, int seed, Metrics metric, int nFolds) throws InterruptedException {
        this.nFolds = nFolds;
        RootWiseTree[] foldsForest = new RootWiseTree[nFolds];
        KFold kfold = new KFold(nFolds);
        kfold.split();
        Random r = new Random(seed);

        System.out.println("iteration \t");
        for (int i = 0; i < forestSize; i++) {
            int newSeed = r.nextInt() + seed;
            for (int j = 0; j < nFolds; j++) {
//                foldsForest[j] = new RootWiseTree(iterations, verboseEval, seed, metric);
                foldsForest[j] = new RootWiseTree(iterations, verboseEval, newSeed, metric);
                foldsForest[j].setValSets(kfold.getTrainIndexes()[j], kfold.getValidIndexes()[j]);
                foldsForest[j].start();
            }
            for (int j = 0; j < nFolds; j++) {
                foldsForest[j].join();
                foldsResults.add(foldsForest[j].getResult());
//                foldsForest[j].setSeed(foldsForest[j].getSeed() + 1);
            }
            forest.add(foldsForest);
            System.out.println(i + "\t" + "valid" + metric.getName() + String.format("%.05f", getFoldsMean()));
            forestResults.add(getFoldsMean());
        }
        System.out.println("Forest" + metric.getName() + ": " + String.format("%.05f", getForestMean()));
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
//                    System.out.println(forest.get(i)[j].getBestExp().processOnTest(k));
                    predictions[k] += 1.0/(1.0 + Math.exp(-forest.get(i)[j].getBestExp().processOnTest(k)));
                }
            }
        }
        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = predictions[i] / (nFolds * forestSize);
        }
        return predictions;
    }

}
