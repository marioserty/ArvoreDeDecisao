/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Arithmetic.ArithmeticExpression;
import CrossValidation.KFold;
import DecisionTrees.LeafWiseTree;
import DecisionTrees.RootWiseTree;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Data.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import Metrics.AUC;

/**
 *
 * @author Mário
 */
public class ArvoreDeDecisao {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Data train = new Data();
        train.Read("train0.csv", 534, 257, ",", true);
        System.out.println("Data info:");
        System.out.println("Data shape: (" + train.numRows + "," + train.numCols + ")");

        int iterations = 5000;
        int verbosity = 1;
        int verboseEval = 100;
        int seed = 1997;

        double[] preds;
        
        KFold kfold = new KFold(train, 5);
        kfold.split();
        while (kfold.nextFold()) {            
            RootWiseTree rwt = new RootWiseTree(kfold.getTrain(), iterations, verboseEval, verbosity, seed);
            rwt.run();
            preds = rwt.predict(kfold.getTest());
            System.out.println("Fold " + kfold.getCurrentFold() + " AUC: " + AUC.measure(kfold.getTest().target, preds));
        }

//        RootWiseTree rwt = new RootWiseTree(train, iterations, verboseEval, verbosity, seed);
//        rwt.run();
//        rwt.saveTreeEquation("eq.txt");
//        LeafWiseTree lft = new LeafWiseTree(iterations, verboseEval, verbosity, seed);
//        lft.run();
    }

}
