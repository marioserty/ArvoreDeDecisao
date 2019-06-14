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
import DecisionTrees.LeafWiseTree;
import Metrics.AUC;

/**
 *
 * @author Mário
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //Dataset reading
        Data train = new Data();
        Data test = new Data();
//        train.Read("datasets/kyphosis_reshape.csv", 81, 4, ",", true);
//        train.CustomRead("datasets/readmission_data_for_modeling.csv", 25000, 83, ",", true);
        train.CustomRead("datasets/diabetic_data.csv", 101766, 48, ",", true);
//        test = train.splitTrainTest(0.7);
        System.out.println("Data info:");
        System.out.println("Data shape: (" + train.numRows + "," + train.numCols + ")");

        // Tree parameters
        int seed = 1997;
        int iterations = 500;
        int verbosity = 1;
        int verboseEval = 100;
        int nfolds = 10;
        RootWiseTree rwt;
        LeafWiseTree lwt;

        double[] preds;
        
        KFold kfold = new KFold(train, nfolds);
        kfold.split();
        int k = 1;
        while (kfold.nextFold()) {            
            
//            rwt = new RootWiseTree(kfold.getTrain(), iterations, verboseEval, verbosity, seed);
//            rwt.run();
//            preds = rwt.predict(kfold.getTest());
            
            lwt = new LeafWiseTree(kfold.getTrain(), iterations, verboseEval, verbosity, seed);
            lwt.run();
            preds = lwt.predict(kfold.getTest());
            
            System.out.println("Fold " + k + " AUC: " + AUC.measure(kfold.getTest().target, preds));
            k++;
        }
    }

}
