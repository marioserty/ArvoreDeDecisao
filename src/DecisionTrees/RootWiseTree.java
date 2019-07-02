/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Arithmetic.Addition;
import Data.Data;
import Arithmetic.ArithmeticExpression;
import Arithmetic.Constant;
import Arithmetic.Multiplication;
import Arithmetic.Subtraction;
import Arithmetic.Variable;
import Metrics.Metrics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class RootWiseTree extends DecisionTree {

    private Random r;
    private int seed;
    private final Metrics metric;
    private final int iterations;
    private final int verboseEval;
    private ArrayList<Integer> trainIndexes;
    private ArrayList<Integer> valIndexes;
    private ArithmeticExpression bestExp;
    private double result;

    /**
     *
     * @param iterations
     * @param verboseEval
     * @param seed
     * @param metric
     */
    public RootWiseTree(int iterations, int verboseEval, int seed, Metrics metric) {
        this.iterations = iterations;
        this.verboseEval = verboseEval;
        this.metric = metric;
        this.seed = seed;
        this.r = new Random(this.seed);
    }

    public void setValSets(ArrayList<Integer> train, ArrayList<Integer> valid) {
        trainIndexes = train;
        valIndexes = valid;
    }

    public void train() {
        bestExp = geraAlturaTres();
        ArithmeticExpression currentExp = (ArithmeticExpression) getBestExp().clone();
        for (int i = 0; i < iterations; i++) {
            currentExp = mutacao(currentExp);
            if (EvaluateOnFoldedTrain(currentExp) > EvaluateOnFoldedTrain(getBestExp())) {
                bestExp = currentExp;
            } else {
                currentExp = getBestExp();
            }
            if (i % verboseEval == 0) {
                System.out.println("Iteration " + i + "\t"
                        + " train-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTrain(bestExp)) + "\t"
                        + " valid-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTest(bestExp)));
            }
        }
        result = EvaluateOnFoldedTest(bestExp);
    }

    @Override
    public void run() {
        bestExp = geraAlturaTres();
        ArithmeticExpression currentExp = (ArithmeticExpression) getBestExp().clone();
        for (int i = 0; i < iterations; i++) {
            currentExp = mutacao(currentExp);
            if (EvaluateOnFoldedTrain(currentExp) > EvaluateOnFoldedTrain(getBestExp())) {
                bestExp = currentExp;
            } else {
                currentExp = getBestExp();
            }
        }
        result = EvaluateOnFoldedTest(bestExp);
    }

    public void saveExpressions(String fileName) {
//        BufferedWriter writer;
//        try {
//            for (int i = 0; i < forest.length; i++) {
//                writer = new BufferedWriter(new FileWriter("expressions/" + fileName + "_" + i + ".txt"));
//                writer.write(forest[i].toString());
//            }
//            System.out.println("Expressions saved successfully!");
//        } catch (IOException ex) {
//            System.out.println("Error while writing expression: " + ex.getMessage());
//        }
    }

    public double Evaluate(ArithmeticExpression exp) {

        double[] preds = new double[Data.numRows];

        for (int i = 0; i < preds.length; i++) {
            preds[i] = exp.process(Data.numRows);
        }
        return metric.measure(Data.target, preds);
    }

    public double EvaluateOnFoldedTrain(ArithmeticExpression exp) {

        double[] preds = new double[trainIndexes.size()];
        int[] target = new int[trainIndexes.size()];

        for (int i = 0; i < preds.length; i++) {
            preds[i] = exp.process(trainIndexes.get(i));
            target[i] = Data.target[trainIndexes.get(i)];
        }
        return metric.measure(target, preds);
    }

    public double EvaluateOnFoldedTest(ArithmeticExpression exp) {
        double[] preds = new double[valIndexes.size()];
        int[] target = new int[valIndexes.size()];

        for (int i = 0; i < preds.length; i++) {
            target[i] = Data.target[valIndexes.get(i)];
            preds[i] = exp.process(valIndexes.get(i));
        }
        return metric.measure(target, preds);
    }

    private ArithmeticExpression geraAlturaUm() {
        if (r.nextDouble() < 0.5) {
            return new Constant(r.nextDouble());
        } else {
            return new Variable((int) (r.nextDouble() * Data.numCols - 1));
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
                return new Addition(mutacao(geraAlturaTres()), exp.getLeft());
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(mutacao(geraAlturaTres()), exp.getLeft());
            } else {
                return new Multiplication(mutacao(geraAlturaTres()), exp.getLeft());
            }
        } else if (d < 2.0 / 4.0) {
            double d2 = r.nextDouble();
            if (d2 < 1.0 / 3.0) {
                return new Addition(exp.getRight(), mutacao(geraAlturaTres()));
            } else if (d2 < 2.0 / 3.0) {
                return new Subtraction(exp.getRight(), mutacao(geraAlturaTres()));
            } else {
                return new Multiplication(exp.getRight(), mutacao(geraAlturaTres()));
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

    public double getResult() throws InterruptedException {
        return result;
    }

    public ArithmeticExpression getBestExp() {
        return bestExp;
    }

    public void setSeed(int newSeed) {
        this.seed = newSeed;
        r.setSeed(seed);
    }

    public int getSeed() {
        return seed;
    }

    public String getMetric() {
        return metric.getName();
    }

}
