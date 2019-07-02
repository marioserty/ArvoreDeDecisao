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
import DecisionTrees.RootWiseTree;
import Metrics.*;

/**
 *
 * @author Mário
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        //Dataset reading
//        Data.CustomRead("datasets/readmission_data_for_modeling.csv", 25000, 83, ",");
//        Data.ReadTrain("datasets/breast_cancer_reshaped.csv", 569, 31, ",");
        Data.ReadTrain("datasets/titanic/reshaped_train.csv", 891, 9, ",");
        Data.ReadTest("datasets/titanic/reshaped_test.csv", 418, 8, ",");

        // Tree parameters
        int seed = 1997;
        int iterations = 1000;
        int forestSize = 50;
        int nFolds = 5;
        int verbose = 1_00;
        Metrics metric = new Accuracy();

        // Parallel training code
        Forest f = new Forest(forestSize);
        f.crossValidate(iterations, verbose, forestSize, seed, metric, nFolds);
        double[] preds = f.predict();
        Data.WritePredictions("datasets/titanic/sub.csv", "datasets/titanic/gender_submission.csv", preds);
        
        System.out.println("Finished!");
        

        // Sequential training code
//        KFold kfold = new KFold(nFolds);
//        kfold.split();
//        double mean = 0.0;
//
//        RootWiseTree rwt;
//        for (int i = 0; i < nFolds; i++) {
//            rwt = new RootWiseTree(iterations, verbose, seed, metric);
//            rwt.setValSets(kfold.getTrainIndexes()[i], kfold.getValidIndexes()[i]);
//            rwt.train();
//            mean += rwt.getResult();
//            System.out.println("Fold " + (i + 1) + " " + metric.getName() + " " + rwt.getResult());
//        }
//        System.out.println("Full : " + mean / nFolds);
    }

}
