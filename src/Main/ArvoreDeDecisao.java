/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Arithmetic.ArithmeticExpression;
import DecisionTrees.RootWiseTree;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Reader.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import Metrics.AUC;

/**
 *
 * @author Mário
 */
public class ArvoreDeDecisao {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println("Data info:");
        Data.readTrain("train0.csv", 534, 257, ",");
        System.out.println("Train shape: (" + Data.trainNumRows + "," + Data.trainNumCols + ")");
        Data.columnsNames();

        int iterations  = 10_000;
        int verbosity   = 1;
        int verboseEval = 100;
        int seed        = 1997;
        
        RootWiseTree rwt = new RootWiseTree(iterations, verboseEval, verbosity, seed);
        rwt.run();
        rwt.saveTreeEquation("eq.txt");
        
    }

    public static double AUROC(ArithmeticExpression exp) {
        double[] probability = new double[Data.target.length];
        for (int i = 0; i < Data.target.length; i++) {
            probability[i] = exp.process(i);
        }
        return AUC.measure(Data.target, probability);
    }

    public static int profit(ArithmeticExpression exp) {
        int p = 0;
        for (int i = 0; i < Data.target.length; i++) {
            if (Math.round(exp.process(i)) == 0 && Data.target[i] == 1) {
                p = p - 5;
            } else if (Math.round(exp.process(i)) == 1 && Data.target[i] == 1) {
                p = p + 5;
            } else if (Math.round(exp.process(i)) == 1 && Data.target[i] == 0) {
                p = p - 25;
            }
        }
        return p;
    }

    public static double sigm(ArithmeticExpression exp, int instancia) {
        return 1.0 / (1.0 + Math.pow(Math.E, -exp.process(instancia)));
    }

    public static double erro(ArithmeticExpression exp, int instancia) {
        return Math.pow(Data.target[instancia] - sigm(exp, instancia), 2);
    }

    public static double erroFuncao(ArithmeticExpression exp) {
        double e = 0.0;

        for (int i = 0; i < 1879; i++) {
            e = e + erro(exp, i);
        }

        return e;
    }

}
