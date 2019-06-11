/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Reader.Data;
import Main.TreeGenerator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import Metrics.AUC;
import Main.ArithmeticExpressions;

/**
 *
 * @author mario
 */
public class RootWiseTree implements Runnable {

    private final int iterations;
    private final int seed;
    private final int verboseEval;
    private final int verbosity;
    private int bestIteration;
    private ArithmeticExpressions bestExpression;

    /**
     *
     * @param iterations
     * @param verboseEval
     * @param verbosity 0 for silent, 1 otherwise
     * @param seed
     */
    public RootWiseTree(int iterations, int verboseEval, int verbosity, int seed) {
        this.iterations = iterations;
        this.seed = seed;
        this.verboseEval = verboseEval;
        this.verbosity = verbosity;
    }

    @Override
    public void run() {
        TreeGenerator g = new TreeGenerator(seed);
        bestExpression = g.geraAlturaTres();
        ArithmeticExpressions e2 = (ArithmeticExpressions) bestExpression.clone();
        for (int i = 0; i < iterations; i++) {
            e2 = g.mutacao(e2);
            if (AUROC(e2) > AUROC(bestExpression)) {
                bestExpression = e2;
                bestIteration = i;
            } else {
                e2 = bestExpression;
            }
            if (i % verboseEval == 0 && verbosity == 1) {
                System.out.println("Iteration " + i + "/" + iterations + " AUC: " + AUROC(bestExpression));
            }
        }
        System.out.println("Best iteration: " + bestIteration + " AUC: " + AUROC(bestExpression));
    }

    public void saveTreeEquation(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("formulas.txt"));
        writer.write(bestExpression.toString());
        writer.close();
    }

    public double AUROC(ArithmeticExpressions exp) {
        double[] probability = new double[Data.target.length];
        for (int i = 0; i < Data.target.length; i++) {
            probability[i] = exp.processa(i);
        }
        return AUC.measure(Data.target, probability);
    }

}
