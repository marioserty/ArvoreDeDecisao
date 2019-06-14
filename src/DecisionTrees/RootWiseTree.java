/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Arithmetic.Addition;
import Data.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import Metrics.AUC;
import Arithmetic.ArithmeticExpression;
import Arithmetic.Constant;
import Arithmetic.Multiplication;
import Arithmetic.Subtraction;
import Arithmetic.Variable;
import java.util.Random;

/**
 *
 * @author mario
 */
public class RootWiseTree implements Runnable {

    private final int iterations;
    private final int seed;
    private final int verboseEval;
    private final int verbosity;
    private final Random r;
    private int bestIteration;
    private ArithmeticExpression bestExpression;
    private Data d;

    /**
     *
     * @param iterations
     * @param verboseEval
     * @param verbosity 0 for silent, 1 otherwise
     * @param seed
     */
    public RootWiseTree(Data dataset, int iterations, int verboseEval, int verbosity, int seed) {
        this.iterations = iterations;
        this.seed = seed;
        this.verboseEval = verboseEval;
        this.verbosity = verbosity;
        this.r = new Random(seed);
        this.d = dataset;
    }

    @Override
    public void run() {
        bestExpression = geraAlturaTres();
        ArithmeticExpression e2 = (ArithmeticExpression) bestExpression.clone();
        for (int i = 0; i < iterations; i++) {
            e2 = mutacao(e2);
            if (AUROC(e2) > AUROC(bestExpression)) {
                bestExpression = e2;
                bestIteration = i;
            } else {
                e2 = bestExpression;
            }
            if (i % verboseEval == 0 && verbosity == 1) {
                System.out.println("Iteration " + i + "/" + iterations + "\t train AUC: " + AUROC(bestExpression));
            }
        }
        System.out.println("Best iteration: " + bestIteration + "\t train-AUC: " + AUROC(bestExpression));
    }

    public void saveTreeEquation(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("formulas.txt"));
        writer.write(bestExpression.toString());
        writer.close();
    }
    
    public double[] predict(Data d){
        double[] preds = new double[d.numRows];
        for (int i = 0; i < preds.length; i++) {
            preds[i] = bestExpression.process(d, i);
        }
        return preds;
    }

    public double AUROC(ArithmeticExpression exp) {
        double[] probability = new double[d.target.length];
        for (int i = 0; i < d.target.length; i++) {
            probability[i] = exp.process(d, i);
        }
        return AUC.measure(d.target, probability);
    }

    private ArithmeticExpression geraAlturaUm() {
        if (r.nextDouble() < 0.5) {
            return new Constant(r.nextDouble());
        } else {
            return new Variable((int) (r.nextDouble() * d.numCols - 1));
        }
    }

    private ArithmeticExpression geraAlturaDois() {
        ArithmeticExpression direita = geraAlturaUm();
        ArithmeticExpression esquerda = geraAlturaUm();

        if (r.nextDouble() < 1.0 / 3.0) {
            return new Addition(esquerda, direita);
        } else if (r.nextDouble() < 2.0 / 3.0) {
            return new Subtraction(esquerda, direita);
        } else {
            return new Multiplication(esquerda, direita);
        }

    }

    private ArithmeticExpression geraAlturaTres() {
        ArithmeticExpression direita;
        ArithmeticExpression esquerda;

        if (r.nextDouble() < 1.0 / 3.0) {
            direita = geraAlturaDois();
            esquerda = geraAlturaUm();
        } else if (r.nextDouble() < 2.0 / 3.0) {
            direita = geraAlturaUm();
            esquerda = geraAlturaDois();
        } else {
            direita = geraAlturaDois();
            esquerda = geraAlturaDois();
        }

        if (r.nextDouble() < 1.0 / 3.0) {
            return new Addition(esquerda, direita);
        } else if (r.nextDouble() < 2.0 / 3.0) {
            return new Subtraction(esquerda, direita);
        } else {
            return new Multiplication(esquerda, direita);
        }
    }

    private ArithmeticExpression mutacao(ArithmeticExpression exp) {
        /**
         * apagar esquerda e gerar alt3, 2, apagar direita e gerar alt3, 2
         * trocar meio;
         */

        double d = r.nextDouble();

        if (d < 1.0 / 4.0) {
            double d2 = r.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(mutacao(geraAlturaTres()), exp);
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(mutacao(geraAlturaTres()), exp);
            } else {
                return new Multiplication(mutacao(geraAlturaTres()), exp);
            }
        } else if (d < 2.0 / 4.0) {
            double d2 = r.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(exp, mutacao(geraAlturaTres()));
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(exp, mutacao(geraAlturaTres()));
            } else {
                return new Multiplication(exp, mutacao(geraAlturaTres()));
            }
        } else if (d < 3.0 / 4.0) {
            double d2 = r.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(exp.getLeft(), geraAlturaDois());
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(exp.getLeft(), geraAlturaDois());
            } else {
                return new Multiplication(exp.getLeft(), geraAlturaDois());
            }
        } else {
            double d2 = r.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(geraAlturaDois(), exp.getRight());
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(geraAlturaDois(), exp.getRight());
            } else {
                return new Multiplication(geraAlturaDois(), exp.getRight());
            }
        }

    }

}
