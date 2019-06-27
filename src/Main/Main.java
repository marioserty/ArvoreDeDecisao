/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import CrossValidation.KFold;
import DecisionTrees.RootWiseTree;
import java.io.FileNotFoundException;
import java.io.IOException;
import Data.Data;
import Metrics.*;

/**
 *
 * @author Mário
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        //Dataset reading
//        Data.CustomRead("datasets/readmission_data_for_modeling.csv", 25000, 83, ",");
        Data.Read("datasets/breast_cancer_reshaped.csv", 569, 31, ",");
        
        // Tree parameters
        int seed        = 1980;
        int iterations  = 3_000;
        int verbose     = 1_000;
        int forestSize  = 500;
        int k           = 5;
        Metrics metric  = new MAE();

        KFold kfold = new KFold(k);
        kfold.split();
        double mean = 0.0;

        RootWiseTree rwt = new RootWiseTree(iterations, verbose, forestSize, seed, metric);
        for (int i = 0; i < k; i++) {
            rwt.setValSets(kfold.getTrainIndexes()[i], kfold.getValidIndexes()[i]);
            rwt.train();
            mean += rwt.EvaluateOnFoldedTest(rwt.getBestExp());
            System.out.println("Fold " + (i + 1) + " " + metric.getName() + " " + rwt.EvaluateOnFoldedTest(rwt.getBestExp()));
        }
        System.out.println("Full : " + mean/k);
    }

}
