/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Arithmetic.ArithmeticExpression;
import CrossValidation.KFold;
import DecisionTrees.RootWiseTree;
import java.io.FileNotFoundException;
import java.io.IOException;
import Data.Data;
import DecisionTrees.Forest;
import Metrics.*;

/**
 *
 * @author Mário
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        //Dataset reading
        Data.CustomRead("datasets/readmission_data_for_modeling.csv", 25000, 83, ",");
//        Data.Read("datasets/breast_cancer_reshaped.csv", 569, 31, ",");

        // Tree parameters
        int seed = 1997;
        int iterations = 1_000;
        int verbose = 1_00;
        int forestSize = 1;
        int nFolds = 5;
        Metrics metric = new AUC();

        // Parallel training code
        Forest f = new Forest(forestSize);
        f.crossValidate(iterations, verbose, forestSize, seed, metric, nFolds);
        System.out.println("Finished!");

        // Sequential training code
        KFold kfold = new KFold(nFolds);
        kfold.split();
        double mean = 0.0;

        RootWiseTree rwt;
        for (int i = 0; i < nFolds; i++) {
            rwt = new RootWiseTree(iterations, verbose, seed, metric);
            rwt.setValSets(kfold.getTrainIndexes()[i], kfold.getValidIndexes()[i]);
            rwt.train();
            mean += rwt.getResult();
            System.out.println("Fold " + (i + 1) + " " + metric.getName() + " " + rwt.getResult());
        }
        System.out.println("Full : " + mean / nFolds);
    }

}
