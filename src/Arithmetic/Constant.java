/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

/**
 *
 * @author Mário
 */
public final class Constant extends Unary {

    double value;

    public Constant(double v) {
        this.value = v;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instancia) {
        return this.value;
    }

    @Override
    public double processOnTest(int instancia) {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public void setValor(double newV) {
        this.value = newV;
    }
}
