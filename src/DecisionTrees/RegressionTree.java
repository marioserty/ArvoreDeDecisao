/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Trigonometric.Tanh;
import Trigonometric.Arctan;
import Trigonometric.Sin;
import Trigonometric.Tan;
import Trigonometric.Cos;
import Arithmetic.*;
import Data.Data;
import Metrics.Metrics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mario
 */
public class RegressionTree extends DecisionTree {

    private int seed;
    private int iterations;
    private double featureFrac;
    private Metrics metric;

    private Random r;
    private ArrayList<Integer> trainIndexes;
    private ArrayList<Integer> valIndexes;
    private ArrayList<Integer> trainCols;
    private ArithmeticExpression bestExp;
    private ArithmeticExpression currentExp;
    private double result;
    private int depth;

    /**
     *
     * @param iterations
     * @param seed
     * @param metric
     * @param featureFrac
     */
    public RegressionTree(int iterations, int seed, Metrics metric, double featureFrac) {
        this.iterations = iterations;;
        this.metric = metric;
        this.seed = seed;
        this.r = new Random(this.seed);
        this.featureFrac = featureFrac;
        this.trainCols = new ArrayList<>();

        while (trainCols.size() < (int) (featureFrac * Data.numCols)) {
            int column = r.nextInt(Data.numCols);
            if (!trainCols.contains(column)) {
                trainCols.add(column);
            }
        }
//        for (int i = 0; i < trainCols.size(); i++) {
//            System.out.print(Data.columns[trainCols.get(i)] + " ");
//        }
//        System.out.println("");
    }

    public void setValSets(ArrayList<Integer> train, ArrayList<Integer> valid) {
        trainIndexes = train;
        valIndexes = valid;
    }

    public void train() {
        bestExp = geraAlturaTres();
        currentExp = (ArithmeticExpression) getBestExp().clone();
        for (int i = 0; i < iterations; i++) {
            mutacao(currentExp);
            if (EvaluateOnFoldedTrain(currentExp) > EvaluateOnFoldedTrain(getBestExp())) {
                bestExp = currentExp;
            } else {
                currentExp = getBestExp();
            }

            System.out.println("Iteration " + i + "\t"
                    + " train-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTrain(bestExp)) + "\t"
                    + " valid-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTest(bestExp)));

        }
        result = EvaluateOnFoldedTest(bestExp);
    }

    @Override
    public void run() {
        bestExp = geraAlturaTres();
        ArithmeticExpression currentExp = (ArithmeticExpression) getBestExp().clone();
        for (int i = 0; i < iterations; i++) {
            currentExp = mutacao(bestExp);
            if (EvaluateOnFoldedTrain(currentExp) > EvaluateOnFoldedTrain(bestExp)) {
                bestExp = (ArithmeticExpression) currentExp.clone();
            }
        }
        result = EvaluateOnFoldedTest(bestExp);
    }

    public double[] predict() {
        double[] preds = new double[Data.test.length];
        for (int i = 0; i < preds.length; i++) {
            preds[i] = 1.0 / (1.0 + Math.exp(-bestExp.processOnTest(i)));
        }
        return preds;
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

//    public double Evaluate(ArithmeticExpression exp) {
//
//        double[] preds = new double[Data.numRows];
//
//        for (int i = 0; i < preds.length; i++) {
//            preds[i] = exp.process(Data.numRows);
//        }
//        return metric.measure(Data.target, preds);
//    }
    public double EvaluateOnFoldedTrain(ArithmeticExpression exp) {

        double[] preds = new double[trainIndexes.size()];
        int[] target = new int[trainIndexes.size()];

        for (int i = 0; i < preds.length; i++) {
            preds[i] = 1.0 / (1.0 + Math.exp(-exp.processOnTrain(trainIndexes.get(i))));
            target[i] = Data.target[trainIndexes.get(i)];
        }
        return metric.measure(target, preds);
    }

    public double EvaluateOnFoldedTest(ArithmeticExpression exp) {
        double[] preds = new double[valIndexes.size()];
        int[] target = new int[valIndexes.size()];

        for (int i = 0; i < preds.length; i++) {
            preds[i] = 1.0 / (1.0 + Math.exp(-exp.processOnTrain(valIndexes.get(i))));
            target[i] = Data.target[valIndexes.get(i)];
        }
        return metric.measure(target, preds);
    }

//    private ArithmeticExpression geraAlturaUm() {
//        if (r.nextDouble() < 0.5) {
//            return new Constant(r.nextDouble());
//        } else {
//            return new Variable((int) (r.nextDouble() * Data.numCols - 1));
//        }
//    }
    private ArithmeticExpression geraAlturaUm() {
        switch (r.nextInt(10)) {
            case 0:
                return new Constant(r.nextDouble());
            case 1:
                return new Constant(-r.nextDouble());
            case 2:
                return new Constant(r.nextInt());
            case 3:
                return new Constant(-r.nextInt());
            case 4:
                return new Variable(getTrainCols().get(r.nextInt(getTrainCols().size())));
            case 5:
                return new Sin(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 6:
                return new Cos(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 7:
                return new Tan(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 8:
                return new Tanh(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 9:
                return new Arctan(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
        }
        return null;
    }

    private ArithmeticExpression geraAlturaDois() {

        ArithmeticExpression left = geraAlturaUm();
        ArithmeticExpression right = geraAlturaUm();

        switch (r.nextInt(4)) {
            case 0:
                return new Addition(left, right);
            case 1:
                return new Subtraction(left, right);
            case 2:
                return new Multiplication(left, right);
            case 3:
                return new Exponentiation(left, right);
        }
        return null;
    }

    private ArithmeticExpression geraAlturaTres() {
        
        ArithmeticExpression right;
        ArithmeticExpression left;

        if (r.nextDouble() < 1.0 / 3.0) {
            right = geraAlturaDois();
            left = geraAlturaUm();
        } else if (r.nextDouble() < 2.0 / 3.0) {
            right = geraAlturaUm();
            left = geraAlturaDois();
        } else {
            right = geraAlturaDois();
            left = geraAlturaDois();
        }

        if (r.nextDouble() < 1.0 / 4.0) {
            return new Addition(left, right);
        } else if (r.nextDouble() < 2.0 / 4.0) {
            return new Subtraction(left, right);
        } else if (r.nextDouble() < 3.0 / 4.0) {
            return new Multiplication(left, right);
        } else {
            return new Exponentiation(left, right);
        }
    }

    private ArithmeticExpression mutacao(ArithmeticExpression exp) {
        /**
         * apagar esquerda e gerar alt3, 2, apagar direita e gerar alt3, 2
         * trocar meio;
         */
        ArithmeticExpression right;
        ArithmeticExpression left;

        double side = r.nextDouble();

        if (side < 0.15) {
            left = bestExp.getLeft();
            right = mutacao(geraAlturaDois());
        } else if (side < 0.25) {
            left = mutacao(geraAlturaDois());
            right = bestExp.getRight();
        } else if (side < 0.5) {
            left = geraAlturaDois();
            right = bestExp.getRight();
        } else {
            left = bestExp.getRight();
            right = geraAlturaDois();

        }

        double operation = r.nextDouble();

        if (operation < 0.25) {
            return new Addition(left, right);
        } else if (operation < 0.50) {
            return new Subtraction(left, right);
        } else if (operation < 0.75) {
            return new Multiplication(left, right);
        } else {
            return new Exponentiation(left, right);
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

    public ArrayList<Integer> getTrainCols() {
        return trainCols;
    }

}
