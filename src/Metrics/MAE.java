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
public class MAE implements Metrics {

    @Override
    public double measure(int[] label, double[] preds) {
        if (label.length != label.length) {
            throw new IllegalArgumentException(String.format("The vector sizes don't match: %d != %d.", label.length, preds.length));
        }
        double sumError = 0.0;
        for (int i = 0; i < label.length; i++) {
            sumError += Math.abs(preds[i] - label[i]);
        }
        return sumError/label.length;
    }

    @Override
    public String getName() {
        return " MAE ";
    }

}
