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

        Data train = new Data("train0.csv", 534, 257, ",", true);
        System.out.println("Data info:");
        System.out.println("Data shape: (" + train.numRows + "," + train.numCols + ")");

        int iterations = 1_000;
        int verbosity = 1;
        int verboseEval = 100;
        int seed = 1997;

        KFold kfold = new KFold(train, 5);
        kfold.split();
        for (int k = 0; k < kfold.getnFolds(); k++) {
            RootWiseTree rwt = new RootWiseTree(train, iterations, verboseEval, verbosity, seed);
            rwt.run();
//            System.out.println("Fold " + k + 1 + " AUC: " + );
        }

        RootWiseTree rwt = new RootWiseTree(train, iterations, verboseEval, verbosity, seed);
        rwt.run();
        rwt.saveTreeEquation("eq.txt");
        LeafWiseTree lft = new LeafWiseTree(iterations, verboseEval, verbosity, seed);
        lft.run();

    }

    public double AUROC(Data d, ArithmeticExpression exp) {
        double[] probability = new double[d.target.length];
        for (int i = 0; i < d.target.length; i++) {
            probability[i] = exp.process(d, i);
        }
        return AUC.measure(d.target, probability);
    }

}
