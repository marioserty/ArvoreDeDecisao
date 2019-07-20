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
import Trigonometric.Log10;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mario
 */
public class RegressionTree extends DecisionTree {

    private int seed;
    private int iterations;
    private double featureFraction;
    private double samplesFraction;
    private Metrics metric;

    private Random random;
    private ArrayList<Integer> trainIndexes;
    private ArrayList<Integer> valIndexes;
    private ArrayList<Integer> trainCols;
    private ArithmeticExpression bestExp;
    private ArithmeticExpression currentExp;

    private double result;
    private double[] trainPreds;
    private double[] valPreds;
    private int[] valTarget;
    private int[] trainTarget;

    /**
     *
     * @param iterations number of training iterations
     * @param seed seed for random number generator
     * @param metric metric to be evaluated
     * @param featureFraction percentage of features to train (from 0 to 1)
     * @param samplesFraction percentage of samples to train (from 0 to 1)
     */
    public RegressionTree(int iterations, int seed, Metrics metric, double featureFraction, double samplesFraction) {
        // Initialize tree parameters
        this.iterations = iterations;;
        this.metric = metric;
        this.seed = seed;
        this.random = new Random(this.seed);
        this.featureFraction = featureFraction;
        this.samplesFraction = samplesFraction;
        this.trainCols = new ArrayList<>();

        // Fraction training features
        while (trainCols.size() < (int) (featureFraction * Data.numCols)) {
            int column = random.nextInt(Data.numCols);
            if (!trainCols.contains(column)) {
                trainCols.add(column);
            }
        }
    }

    public void setValidationSets(ArrayList<Integer> train, ArrayList<Integer> valid) {
        // Fractioning training samples        
        int initialSize = train.size();
        while (train.size() < (int) (initialSize * samplesFraction)) {
            train.remove(random.nextInt(train.size()));
        }
        initialSize = valid.size();
        while (valid.size() < (int) (initialSize * samplesFraction)) {
            valid.add(random.nextInt(valid.size()));
        }
        // Set trainable indexes
        trainIndexes = train;
        valIndexes = valid;
        // Instantiate training and validation predictions
        trainPreds = new double[train.size()];
        valPreds = new double[valid.size()];
        // Set training and validation targets
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
        bestExp = generateDepthTree();
        currentExp = generateDepthTree();
        for (int i = 0; i < iterations; i++) {
            mutation();
            if (EvaluateOnTrain(currentExp) > EvaluateOnTrain(getBestExp())) {
                bestExp = (ArithmeticExpression) currentExp.clone();
            }

            if (i % 100 == 0) {
                System.out.println("Iteration " + i + "\t"
                        + " train-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnTrain(bestExp)) + "\t"
                        + " valid-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnTest(bestExp))
                        + " height: " + bestExp.height());
            }

        }
        result = EvaluateOnTest(bestExp);
    }

    @Override
    public void run() {
        bestExp = generateDepthTree();
        currentExp = generateDepthTree();
        for (int i = 0; i < iterations; i++) {
            mutation();
            if (EvaluateOnTrain(currentExp) > EvaluateOnTrain(getBestExp())) {
                bestExp = (ArithmeticExpression) currentExp.clone();
            }

//            System.out.println("Iteration " + i + "\t"
//                    + " train-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTrain(bestExp)) + "\t"
//                    + " valid-" + metric.getName() + ": " + String.format("%.05f", EvaluateOnFoldedTest(bestExp)));
        }
        result = EvaluateOnTest(bestExp);
    }

    /**
     * predict the test data if it's set
     * @return predictions for test data
     * or null if tree is not trained or if test data has not been set
     */
    public double[] predict() {
        if (bestExp == null) {
            System.err.println("Tree not yet trained! Cannot predict.");
            return null;
        } else if (Data.test == null) {
            System.err.println("Test data not set!");
            return null;
        } else {
            double[] preds = new double[Data.test.length];
            for (int i = 0; i < Data.test.length; i++) {
                if (Double.isNaN(1.0 / (1.0 + Math.exp(-bestExp.processOnTest(i))))) {
                    preds[i] = 0.0;
                } else {
                    preds[i] = 1.0 / (1.0 + Math.exp(-bestExp.processOnTest(i)));
                }
            }
            return preds;
        }
    }

    public double EvaluateOnTrain(ArithmeticExpression exp) {
        for (int i = 0; i < trainPreds.length; i++) {
            trainPreds[i] = 1.0 / (1.0 + Math.exp(-exp.processOnTrain(trainIndexes.get(i))));
        }
        return metric.measure(trainTarget, trainPreds);
    }

    public double EvaluateOnTest(ArithmeticExpression exp) {
        for (int i = 0; i < valPreds.length; i++) {
            valPreds[i] = 1.0 / (1.0 + Math.exp(-exp.processOnTrain(valIndexes.get(i))));
        }
        return metric.measure(valTarget, valPreds);
    }

    private ArithmeticExpression generateDepthOne() {
        switch (random.nextInt(2)) {
            case 0:
                switch (random.nextInt(4)) {
                    case 0:
                        return new Constant(-random.nextInt());
                    case 1:
                        return new Constant(random.nextInt());
                    case 2:
                        return new Constant(-random.nextDouble());
                    case 3:
                        return new Constant(random.nextDouble());
                }
            case 1:
                return new Variable(getTrainCols().get(random.nextInt(getTrainCols().size())));
            default:
                return null;
        }
    }

    private ArithmeticExpression generateDepthTwo() {

        switch (random.nextInt(8)) {
            case 0:
                return new Addition(generateDepthOne(), generateDepthOne());
            case 1:
                return new Subtraction(generateDepthOne(), generateDepthOne());
            case 2:
                return new Multiplication(generateDepthOne(), generateDepthOne());
            case 3:
                return new Exponentiation(generateDepthOne(), generateDepthOne());
            case 4:
                return new Log10(generateDepthOne());
            case 5:
                return new Sin(generateDepthOne());
            case 6:
                return new Cos(generateDepthOne());
            case 7:
                return new Tan(generateDepthOne());
            default:
                return null;
        }
    }

    private ArithmeticExpression generateDepthTree() {

        ArithmeticExpression right;
        ArithmeticExpression left;

        if (random.nextDouble() < 1.0 / 3.0) {
            right = generateDepthTwo();
            left = generateDepthOne();
        } else if (random.nextDouble() < 2.0 / 3.0) {
            right = generateDepthOne();
            left = generateDepthTwo();
        } else {
            right = generateDepthTwo();
            left = generateDepthTwo();
        }

        if (random.nextDouble() < 1.0 / 4.0) {
            return new Addition(left, right);
        } else if (random.nextDouble() < 2.0 / 4.0) {
            return new Subtraction(left, right);
        } else if (random.nextDouble() < 3.0 / 4.0) {
            return new Multiplication(left, right);
        } else {
            return new Exponentiation(left, right);
        }
    }

    private ArithmeticExpression getRandomNode(ArithmeticExpression exp) {
        int side = random.nextInt(4);
        switch (side) {
            case 0:
                return generateDepthTwo();
            case 1:
                return generateDepthTree();
            case 2:
                return getRandomNode(exp.getLeft());
            case 3:
                return getRandomNode(exp.getRight());
            default:
                return null;
        }
    }

    private void mutation() {
        /**
         * apagar esquerda e gerar alt3, 2, apagar direita e gerar alt3, 2
         * trocar meio;
         */
        ArithmeticExpression node;

        double p = random.nextDouble();

        if (p < 0.10) {
            currentExp.setLeft((ArithmeticExpression) bestExp.getLeft().clone());
            currentExp.setRight(getRandomNode((ArithmeticExpression) bestExp.getRight().clone()));
        } else if (p < 0.20) {
            currentExp.setRight((ArithmeticExpression) bestExp.getRight().clone());
            currentExp.setLeft(getRandomNode((ArithmeticExpression) bestExp.getLeft().clone()));
        } else if (p < 0.60) {
            currentExp.setLeft(generateDepthTwo());
            currentExp.setRight((ArithmeticExpression) bestExp.getRight().clone());
        } else {
            currentExp.setLeft((ArithmeticExpression) bestExp.getLeft().clone());
            currentExp.setRight(generateDepthTwo());
        }
    }

    public void generateInitialTree(int height) {
        currentExp = generateDepthTwo();
        while (currentExp.height() < height) {
            currentExp = getRandomNode(bestExp);
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
        random.setSeed(seed);
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
