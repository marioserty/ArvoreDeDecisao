/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

import Data.Data;

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
    public int height() {
        return 1;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public void setValor(double newV) {
        this.value = newV;
    }

    @Override
    public void setRight(ArithmeticExpression exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLeft(ArithmeticExpression exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArithmeticExpression getRight() {
        return this;
    }

    @Override
    public ArithmeticExpression getLeft() {
        return this;
    }
}
