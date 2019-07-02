/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Arithmetic.ArithmeticExpression;
import CrossValidation.KFold;
import Metrics.Metrics;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class Forest extends DecisionTree {

    private final ArrayList<Double> foldsResults;
    private final ArrayList<Double> forestResults;
    private final int forestSize;

    public Forest(int forestSize) {
        foldsResults = new ArrayList<>();
        forestResults = new ArrayList<>();
        this.forestSize = forestSize;
    }

    public void crossValidate(int iterations, int verboseEval, int forestSize, int seed, Metrics metric, int nFolds) throws InterruptedException {

        RootWiseTree[] foldsForest = new RootWiseTree[nFolds];
        KFold kfold = new KFold(nFolds);
        kfold.split();

        for (int i = 0; i < nFolds; i++) {
            foldsForest[i] = new RootWiseTree(iterations, verboseEval, seed, metric);
            foldsForest[i].setValSets(kfold.getTrainIndexes()[i], kfold.getValidIndexes()[i]);
        }

        System.out.println("iteration \t");
        for (int i = 0; i < forestSize; i++) {
            for (int j = 0; j < nFolds; j++) {
                foldsForest[j].start();
            }
            for (int j = 0; j < nFolds; j++) {
                foldsForest[j].join();
                foldsResults.add(foldsForest[j].getResult());
                foldsForest[j].setSeed(foldsForest[j].getSeed() + 1);
            }
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

}
