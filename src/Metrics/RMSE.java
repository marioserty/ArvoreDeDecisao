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
public class RMSE implements Metrics {

    @Override
    public double measure(int[] label, double[] preds) {
        if (label.length != label.length) {
            throw new IllegalArgumentException(String.format("The vector sizes don't match: %d != %d.", label.length, preds.length));
        }
        double[] newLabel;
        double sumError = 0.0;
        for (int i = 0; i < label.length; i++) {
            sumError += Math.pow((preds[i] - label[i]), 2);
        }
        return Math.sqrt(sumError/label.length);

    }

    @Override
    public String getName() {
        return "RMSE";
    }

}
