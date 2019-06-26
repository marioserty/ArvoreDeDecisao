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
import java.util.ArrayList;

/**
 *
 * @author Mário
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        //Dataset reading
        Data train = new Data();
//        train.Read("datasets/kyphosis_reshape.csv", 81, 4, ",", true);
//        train.CustomRead("datasets/readmission_data_for_modeling.csv", 25000, 83, ",", true);
        train.Read("datasets/arquivoX2.csv", 199981, 10, ",", true);
//        test = train.splitTrainTest(0.7);
        System.out.println("Data info:");
        System.out.println("Data shape: (" + train.numRows + "," + train.numCols + ")");
        train.columnsNames();

        // Tree parameters
        int seed = 1980;
        int iterations = 100;
        int verbosity = 1;
        int verboseEval = 1;
        int nfolds = 5;

        double[] preds;
        double meanAUC = 0.0;

        KFold kfold = new KFold(train, nfolds);
        kfold.split();
        RootWiseTree rwt;
        LeafWiseTree lwt;
        int k = 1;
        //Sequential training
        System.out.println("Running kfold...");
        while (kfold.nextFold()) {

            rwt = new RootWiseTree(kfold.getTrain(), iterations, verboseEval, verbosity, seed);
            rwt.run();
            preds = rwt.predict(kfold.getTest());

            meanAUC += AUC.measure(kfold.getTest().target, preds);
            System.out.println("Fold " + k + " AUC: " + AUC.measure(kfold.getTest().target, preds));
            rwt.saveTreeEquation("tree_fold_" + k + ".txt");
            k++;
        }
        System.out.println("Mean AUC: " + meanAUC / nfolds);

//        meanAUC = 0.0;
//        // Training using threads
//        ArrayList<RootWiseTree> trees = new ArrayList<>();
//        for (int i = 0; i < nfolds; i++) {
//            kfold.nextFold();
//            trees.add(new RootWiseTree(kfold.getTrain(), iterations, verboseEval, verbosity, seed));
//            trees.get(i).start();
//        }
//        kfold = new KFold(train, nfolds);
//        kfold.split();
//        for (int i = 0; i < nfolds; i++) {            
//            trees.get(i).join();
//            kfold.nextFold();
//            preds = trees.get(i).predict(kfold.getTest());
//            meanAUC += AUC.measure(kfold.getTest().target, preds);
//            System.out.println("Fold " + (i + 1) + " AUC: " + AUC.measure(kfold.getTest().target, preds));
//        }
//        for (int i = 0; i < nfolds; i++) {
//        }
//        System.out.println("Mean AUC: " + meanAUC/nfolds);
    }

}
