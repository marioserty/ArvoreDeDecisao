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
public class Cos extends Unary {

    ArithmeticExpression exp;

    public Cos(ArithmeticExpression exp) {
        this.exp = exp;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instance) {
        return Math.cos(exp.processOnTrain(instance));
    }

    @Override
    public double processOnTest(int instance) {
        return Math.cos(exp.processOnTest(instance));
    }

    @Override
    public int height() {
        return 1;
    }

    @Override
    public String toString() {
        return "cos(" + exp.toString() + ")";
    }
}
