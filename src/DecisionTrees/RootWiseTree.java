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
public class RootWiseTree extends Thread{

    private final int iterations;
    private final int verboseEval;
    private final Random seed;
    private ArithmeticExpression[] forest;

    /**
     *
     * @param iterations
     * @param verboseEval
     * @param forestSize
     * @param seed
     */
    public RootWiseTree(int iterations, int verboseEval, int forestSize, int seed) {
        this.iterations = iterations;
        this.verboseEval = verboseEval;
        this.seed = new Random(seed);
        forest = new ArithmeticExpression[forestSize];
    }

    public ArithmeticExpression train(int[] trainingIndexes) {
        ArithmeticExpression bestExp = geraAlturaTres();
        ArithmeticExpression currentExp = (ArithmeticExpression) bestExp.clone();
        for (int i = 0; i < iterations; i++) {
            currentExp = mutacao(currentExp);
            if (AUROC(currentExp) > AUROC(bestExp)) {
                bestExp = currentExp;
            } else {
                currentExp = bestExp;
            }
        }
        return bestExp;
    }
    
    public void train(Data d){
        
    }

    public void saveTreeEquation(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("equations/"+fileName))) {
            writer.write(bestExpression.toString(d));
        }
    }
    
    public double[] predict(Data d){
        double[] preds = new double[d.numRows];
        for (int i = 0; i < preds.length; i++) {
            preds[i] = bestExpression.process(d, i);
        }
        return preds;
    }

    public double AUROC(ArithmeticExpression exp, int[] testIndexes) {
        double[] probability = new double[testIndexes.];
        for (int i = 0; i < d.target.length; i++) {
            probability[i] = exp.process(d, i);
        }
        return AUC.measure(d.target, probability);
    }

    private ArithmeticExpression geraAlturaUm() {
        if (seed.nextDouble() < 0.5) {
            return new Constant(seed.nextDouble());
        } else {
            return new Variable((int) (seed.nextDouble() * d.numCols - 1));
        }
    }

    private ArithmeticExpression geraAlturaDois() {
        ArithmeticExpression direita = geraAlturaUm();
        ArithmeticExpression esquerda = geraAlturaUm();

        if (seed.nextDouble() < 1.0 / 3.0) {
            return new Addition(esquerda, direita);
        } else if (seed.nextDouble() < 2.0 / 3.0) {
            return new Subtraction(esquerda, direita);
        } else {
            return new Multiplication(esquerda, direita);
        }

    }

    private ArithmeticExpression geraAlturaTres() {
        ArithmeticExpression direita;
        ArithmeticExpression esquerda;

        if (seed.nextDouble() < 1.0 / 3.0) {
            direita = geraAlturaDois();
            esquerda = geraAlturaUm();
        } else if (seed.nextDouble() < 2.0 / 3.0) {
            direita = geraAlturaUm();
            esquerda = geraAlturaDois();
        } else {
            direita = geraAlturaDois();
            esquerda = geraAlturaDois();
        }

        if (seed.nextDouble() < 1.0 / 3.0) {
            return new Addition(esquerda, direita);
        } else if (seed.nextDouble() < 2.0 / 3.0) {
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

        double d = seed.nextDouble();

        if (d < 1.0 / 4.0) {
            double d2 = seed.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(mutacao(geraAlturaTres()), exp);
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(mutacao(geraAlturaTres()), exp);
            } else {
                return new Multiplication(mutacao(geraAlturaTres()), exp);
            }
        } else if (d < 2.0 / 4.0) {
            double d2 = seed.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(exp, mutacao(geraAlturaTres()));
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(exp, mutacao(geraAlturaTres()));
            } else {
                return new Multiplication(exp, mutacao(geraAlturaTres()));
            }
        } else if (d < 3.0 / 4.0) {
            double d2 = seed.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(exp.getLeft(), geraAlturaDois());
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(exp.getLeft(), geraAlturaDois());
            } else {
                return new Multiplication(exp.getLeft(), geraAlturaDois());
            }
        } else {
            double d2 = seed.nextDouble();
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
