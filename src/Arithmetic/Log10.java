/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trigonometric;

import Arithmetic.ArithmeticExpression;
import Arithmetic.Unary;
import Arithmetic.Variable;
import Data.Data;

/**
 *
 * @author mario
 */
public class Log10 extends Unary {

    ArithmeticExpression exp;

    public Log10(ArithmeticExpression exp) {
        this.exp = exp;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instance) {
        return Math.log10(Math.abs(exp.processOnTrain(instance)));
    }

    @Override
    public double processOnTest(int instance) {
        return Math.log10(Math.abs(exp.processOnTest(instance)));
    }

    @Override
    public int height() {
        return 1;
    }

    @Override
    public String toString() {
        return "log10(" + exp.toString() + ")";
    }

    @Override
    public ArithmeticExpression getRight() {
        return exp;
    }

    @Override
    public ArithmeticExpression getLeft() {
        return exp;
    }

    @Override
    public void setRight(ArithmeticExpression exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLeft(ArithmeticExpression exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
