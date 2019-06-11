/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Arithmetic.ArithmeticExpression;
import Arithmetic.GreaterThan;
import Metrics.AUC;
import Reader.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author mario
 */
public class LeafWiseTree implements Runnable {

    private final int iterations;
    private final int seed;
    private final int verboseEval;
    private final int verbosity;
    private final Random r;
    private int bestIteration;
    private ArithmeticExpression bestExpression;

    public LeafWiseTree(int iterations, int verboseEval, int verbosity, int seed) {
        this.iterations = iterations;
        this.seed = seed;
        this.verboseEval = verboseEval;
        this.verbosity = verbosity;
        this.r = new Random(seed);
    }

    @Override
    public void run() {
        /*bestExpression = geraAlturaTres();
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
                System.out.println("Iteration " + i + "/" + iterations + " AUC: " + AUROC(bestExpression));
            }
        }
        System.out.println("Best iteration: " + bestIteration + " AUC: " + AUROC(bestExpression));*/
    }

    public void saveTreeCode(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("formulas.txt"));
        writer.write(bestExpression.toString());
        writer.close();
    }

    public double AUROC(ArithmeticExpression exp) {
        double[] probability = new double[Data.target.length];
        for (int i = 0; i < Data.target.length; i++) {
            probability[i] = exp.process(i);
        }
        return AUC.measure(Data.target, probability);
    }
    
    public ArithmeticExpression generateDepthOne() {
        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;

        if (r.nextDouble() < 0.5) {
            label = 0;
        } else {
            label = 1;
        }

        return new GreaterThan(variavel, label, geraAlturaTres(), geraAlturaTres());
    }

    public ArithmeticExpression generateDepthTwo() {
        ArithmeticExpression esq = generateDepthOne();
        ArithmeticExpression dir = generateDepthOne();

        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;

        if (r.nextDouble() < 0.5) {
            label = 0;
        } else {
            label = 1;
        }

        return new GreaterThan(variavel, label, esq, dir);
    }

    public ArithmeticExpression geraITE_AlturaTres() {
        ArithmeticExpression esq = geraITE_AlturaDois();
        ArithmeticExpression dir = geraITE_AlturaDois();

        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;

        if (r.nextDouble() < 0.5) {
            label = 0;
        } else {
            label = 1;
        }

        return new GreaterThan(variavel, label, esq, dir);
    }

    public ArithmeticExpression geraITE_AlturaQuatro() {
        ArithmeticExpression esq = geraITE_AlturaTres();
        ArithmeticExpression dir = geraITE_AlturaTres();

        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;

        if (r.nextDouble() < 0.5) {
            label = 0;
        } else {
            label = 1;
        }

        return new GreaterThan(variavel, label, esq, dir);
    }

    public ArithmeticExpression mutacaoIf(ArithmeticExpression ite) {
        Random r = new Random();

        int n = r.nextInt(3);

        if (ite instanceof GreaterThan) {
            switch (n) {
                case 0:
                    int x = r.nextInt(Data.trainNumCols - 1);
                    ((GreaterThan) ite).setLabel(x);
                    break;
                case 1:
                    this.mutacaoIf(((GreaterThan) ite).getLeft());
                    break;
                default:
                    this.mutacaoIf(((GreaterThan) ite).getRight());
                    break;
            }
            return ite;
        } else {
            mutacao(ite);
        }

        return ite;

    }

}
