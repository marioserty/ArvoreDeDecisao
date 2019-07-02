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
public final class Multiplication extends Binary {

    public Multiplication(ArithmeticExpression l, ArithmeticExpression r) {
        super.right = r;
        super.left = l;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instancia) {
        return (super.getLeft().processOnTrain(instancia) * super.getRight().processOnTrain(instancia));
    }

    @Override
    public double processOnTest(int instancia) {
        return (super.getLeft().processOnTest(instancia) * super.getRight().processOnTest(instancia));
    }

    @Override
    public String toString() {
        return ("(" + super.getLeft().toString() + " * " + super.getRight().toString() + ")");
    }

}
