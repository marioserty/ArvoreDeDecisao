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
    double[] trainPreds;
    double[] valPreds;
    int[] valTarget;
    int[] trainTarget;

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

        trainPreds = new double[trainIndexes.size()];
        valPreds = new double[valIndexes.size()];

        trainTarget = new int[trainIndexes.size()];
        for (int i = 0; i < trainTarget.length; i++) {
            trainTarget[i] = Data.target[trainIndexes.get(i)];
        }

        valTarget = new int[valIndexes.size()];
        for (int i = 0; i < valTarget.length; i++) {
            valTarget[i] = Data.target[valIndexes.get(i)];
        }
    }

    public void train() {
//        bestExp = generateInitialTree(6);
        bestExp = geraAlturaTres();
        for (int i = 0; i < iterations; i++) {
            currentExp = mutacao(bestExp);
            if (EvaluateOnFoldedTrain(currentExp) > EvaluateOnFoldedTrain(getBestExp())) {
                bestExp = currentExp;
            }

            System.out.println("Iteration " + i + "\t"
                    + " train-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTrain(bestExp)) + "\t"
                    + " valid-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTest(bestExp))
                    + " height: " + bestExp.height());

        }
        result = EvaluateOnFoldedTest(bestExp);
    }

    @Override
    public void run() {
        bestExp = generateInitialTree(12);
        for (int i = 0; i < iterations; i++) {
            currentExp = mutacao(bestExp);
            if (EvaluateOnFoldedTrain(currentExp) > EvaluateOnFoldedTrain(getBestExp())) {
                bestExp = currentExp;
            }

//            System.out.println("Iteration " + i + "\t"
//                    + " train-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTrain(bestExp)) + "\t"
//                    + " valid-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTest(bestExp)));
        }
        result = EvaluateOnFoldedTest(bestExp);
    }

    public double[] predict() {
        double[] preds = new double[Data.test.length];
        for (int i = 0; i < Data.test.length; i++) {
            if (Double.isNaN(1.0 / (1.0 + Math.exp(-bestExp.processOnTest(i))))) {
                preds[i] = 0.0;
            }else{
                preds[i] = 1.0 / (1.0 + Math.exp(-bestExp.processOnTest(i)));
            }
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

    public double EvaluateOnFoldedTrain(ArithmeticExpression exp) {

        for (int i = 0; i < trainPreds.length; i++) {
            trainPreds[i] = 1.0 / (1.0 + Math.exp(-exp.processOnTrain(trainIndexes.get(i))));
        }
        return metric.measure(trainTarget, trainPreds);
    }

    public double EvaluateOnFoldedTest(ArithmeticExpression exp) {

        for (int i = 0; i < valPreds.length; i++) {
            valPreds[i] = 1.0 / (1.0 + Math.exp(-exp.processOnTrain(valIndexes.get(i))));
        }

        return metric.measure(valTarget, valPreds);
    }

//    private ArithmeticExpression geraAlturaUm() {
//        if (r.nextDouble() < 0.5) {
//            return new Constant(r.nextDouble());
//        } else {
//            return new Variable((int) (r.nextDouble() * Data.numCols - 1));
//        }
//    }
    private ArithmeticExpression geraAlturaUm() {
        switch (r.nextInt(2)) {
            case 0:
                switch (r.nextInt(2)) {
                    case 0:
                        return new Constant(-r.nextInt());
                    case 1:
                        return new Constant(r.nextInt());
                    case 2:
                        return new Constant(-r.nextDouble());
                    case 3:
                        return new Constant(r.nextDouble());
                }
            case 1:
                return new Variable(getTrainCols().get(r.nextInt(getTrainCols().size())));
            case 2:
                return new Sin(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 3:
                return new Cos(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 4:
                return new Tan(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 5:
                return new Tanh(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
            case 6:
                return new Arctan(new Variable(getTrainCols().get(r.nextInt(getTrainCols().size()))));
        }
        return null;
    }

    private ArithmeticExpression geraAlturaDois() {
//        System.out.println("gen Depth 2");

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
//        System.out.println("Mutation");
        ArithmeticExpression left;
        ArithmeticExpression right;

        double side = r.nextDouble();

        if (side < 0.25) {
            left = exp.getLeft();
            right = mutacao(exp.getRight());
        } else if (side < 0.50) {
            left = mutacao(exp.getLeft());
            right = exp.getRight();
        } else if (side < 0.75) {
            left = geraAlturaDois();
            right = exp.getRight();
        } else {
            left = exp.getRight();
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

    public ArithmeticExpression generateInitialTree(int height) {
        
        ArithmeticExpression tree = geraAlturaTres();
        
        while (tree.height() < height) {
            tree = mutacao(tree);
        }
        
        return tree;
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
