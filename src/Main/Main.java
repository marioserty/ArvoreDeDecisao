/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import CrossValidation.KFold;
import java.io.FileNotFoundException;
import java.io.IOException;
import Data.Data;
import DecisionTrees.Forest;
import DecisionTrees.RegressionTree;
import Metrics.*;

/**
 *
 * @author Mário
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        //Dataset reading
//        Data.ReadTrain("../datasets/readmission_data_for_modeling.csv", 25000, 83, ",");
//        Data.ReadTrain("datasets/breast_cancer_reshaped.csv", 569, 31, ",");
//        Data.ReadTrain("../datasets/titanic/reshaped_train.csv", 891, 9, ",");
//        Data.ReadTest("../datasets/titanic/reshaped_test.csv", 418, 8, ",");
//        Data.ReadTrain("datasets/santander/train.csv", 200000, 200, ",");
//        Data.ReadTest("datasets/santander/test.csv", 200000, 199, ",");
        Data.ReadTrain("../datasets/santander-customer-satisfaction/train.csv", 76020, 371, ",");
        Data.ReadTest("../datasets/santander-customer-satisfaction/test.csv", 75818, 370, ",");

        // Tree parameters
        int seed = 2028;
        int iterations = 500;
        int forestSize = 100;
        int nFolds = 4;
        double featureFrac = 1;
        Metrics metric = new AUC();
        boolean forest = false;

        if (forest) {
            // Parallel training code
            Forest f = new Forest();
            f.crossValidate(iterations, forestSize, seed, metric, nFolds, featureFrac);
            f.predict();
//        Data.WritePredictions("../datasets/titanic/sub.csv", "../datasets/titanic/gender_submission.csv", f.preds);
            Data.WritePredictions("../datasets/santander-customer-satisfaction/sub.csv", "../datasets/santander-customer-satisfaction/sample_submission.csv", f.preds);
            System.out.println("Finished!");
        } else {
            // Sequential training code
            KFold kfold = new KFold(nFolds);
            kfold.split();
            double mean = 0.0;

            RegressionTree rwt;
            rwt = new RegressionTree(iterations, seed, metric, featureFrac);
            for (int i = 0; i < nFolds; i++) {
                rwt.setValSets(kfold.getTrainIndexes()[i], kfold.getValidIndexes()[i]);
                rwt.train();
                mean += rwt.getResult();
                System.out.println("Fold " + (i + 1) + " " + metric.getName() + " " + rwt.getResult());
            }
            System.out.println("Full : " + mean / nFolds);
            Data.WritePredictions("../datasets/santander-customer-satisfaction/sub.csv", "../datasets/santander-customer-satisfaction/sample_submission.csv", rwt.predict());
            System.out.println("Finished!");
        }
    }

}
