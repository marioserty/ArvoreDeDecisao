/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;

/**
 *
 * @author mario
 */
public class Accuracy implements Metrics{

    @Override
    public double measure(int[] label, double[] preds) {
        int correct = 0;
        for (int i = 0; i < preds.length; i++) {
            if (((preds[i] > .5) && (label[i] == 1)) || ((preds[i] < .5) && (label[i] == 0))) {
                correct++;
            }
        }
        return correct / ((double)preds.length);
    }

    @Override
    public String getName() {
        return " Accuracy ";
    }
    
}
