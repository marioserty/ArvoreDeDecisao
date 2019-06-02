/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorededecisao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Reader.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import metrics.AUC;

/**
 *
 * @author Mário
 */
public class ArvoreDeDecisao {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        System.out.println("Data info:");
//        Data.readTrain("train.csv", 1879, 10, "\\|");
//        Data.readTest("test.csv", 498121, 9, "\\|");
//        System.out.println("Train shape: (" + Data.trainNumRows + "," + Data.trainNumCols + ")");
//        System.out.println("Test shape: (" + Data.testNumRows + "," + Data.testNumCols + ")");
//        System.out.print("Colnames: ");
//        for (int i = 0; i < Data.columns.length; i++) {
//            System.out.print(Data.columns[i] + ", ");
//        }
//
//        double ensemble = 500.0;
//        int iterations = 200_000;
//        boolean crossValidation = true;
//        ExpressaoAritmetica[] ex = new ExpressaoAritmetica[5];
//
//        GeradorDeArvore g = new GeradorDeArvore();
//        ExpressaoAritmetica e = g.geraAlturaTres();
//        ExpressaoAritmetica e2 = e;
//
//        for (int i = 0; i < iterations; i++) {
//            e2 = g.mutacao(e2);
//            if (profit(e2) > profit(e)) {
//                e = e2;
//                //System.out.println("Erro da função: " + erroFuncao(e));
//                System.out.println("Profit: " + profit(e));
//            } else {
//                e2 = e;
//            }
//            if (i % 10_000 == 0) {
//                System.out.println("it: " + i);
//            }
//        }
//
//    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Data info:");
        Data.readTrain("train0.csv", 534, 257, ",");
        //Data.readTrain("train0u.csv", 534, 47, ",");
        System.out.println("Train shape: (" + Data.trainNumRows + "," + Data.trainNumCols + ")");
        Data.columnsNames();

        int iterations = 5000;
        int verbose = 100;

        GeradorDeArvore g = new GeradorDeArvore();
        ExpressaoAritmetica e = g.geraAlturaTres();
        ExpressaoAritmetica e2 = e;

        String bestf = null;
        int besti = 0;

        for (int i = 0; i < iterations; i++) {
            e2 = g.mutacao(e2);
            if (AUROC(e2) > AUROC(e)) {
                e = e2;
                bestf = e.toString();
                besti = i;
            } else {
                e2 = e;
            }
            if (i % verbose == 0) {
                System.out.print("it: " + i);
                System.out.println(" AUC: " + AUROC(e));
            }
        }
        System.out.println("Formula: \n" + bestf);
        System.out.println("Best iteration: " + besti);
        BufferedWriter writer = new BufferedWriter(new FileWriter("formulas.txt"));
        writer.write(bestf);
        writer.close();

    }

    public static double AUROC(ExpressaoAritmetica exp) {
        double[] probability = new double[Data.target.length];
        for (int i = 0; i < Data.target.length; i++) {
            probability[i] = exp.processa(i);
        }
        return AUC.measure(Data.target, probability);
    }

    public static int profit(ExpressaoAritmetica exp) {
        int p = 0;
        for (int i = 0; i < Data.target.length; i++) {
            if (Math.round(exp.processa(i)) == 0 && Data.target[i] == 1) {
//                System.out.println("Pred: " + Math.round(exp.processa(i)) + ", Label: " + Data.target[i]);
                p = p - 5;
            } else if (Math.round(exp.processa(i)) == 1 && Data.target[i] == 1) {
//                System.out.println("Pred: " + Math.round(exp.processa(i)) + ", Label: " + Data.target[i]);
                p = p + 5;
            } else if (Math.round(exp.processa(i)) == 1 && Data.target[i] == 0) {
//                System.out.println("Pred: " + Math.round(exp.processa(i)) + ", Label: " + Data.target[i]);
                p = p - 25;
            }
        }
        return p;
    }

    public static double sigm(ExpressaoAritmetica exp, int instancia) {
        return 1.0 / (1.0 + Math.pow(Math.E, -exp.processa(instancia)));
    }

    public static double erro(ExpressaoAritmetica exp, int instancia) {
        return Math.pow(Data.target[instancia] - sigm(exp, instancia), 2);
    }

    public static double erroFuncao(ExpressaoAritmetica exp) {
        double e = 0.0;

        for (int i = 0; i < 1879; i++) {
            e = e + erro(exp, i);
//            System.out.println(e);
        }

        return e;
    }

    //Leave One Out Cross Validation
    public static double LOOCV(ExpressaoAritmetica exp) {
        double acertos = 0;

        for (int i = 0; i < 275; i++) {
//            System.out.println("erro total: " + erroFuncao(exp) + " erro sem " + (i+1) +": " + erro(exp, i+1));
//            System.out.println(Math.round(exp.processa(i)) + " - " + Dados.saidaDesejada[i][1]);
            if (Math.round(sigm(exp, i)) == (int) Data.target[i]) {
                acertos = acertos + 1;
            }
        }

        return acertos / 275.0;
    }

}
