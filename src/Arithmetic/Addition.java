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
public final class Addition extends Binary {

    public Addition(ArithmeticExpression esq, ArithmeticExpression dir) {
        super.right = dir;
        super.left = esq;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instancia) {
        return (super.getLeft().processOnTrain(instancia) + super.getRight().processOnTrain(instancia));
    }
    
    @Override
    public double processOnTest(int instancia) {
        return (super.getLeft().processOnTest(instancia) + super.getRight().processOnTest(instancia));
    }

    @Override
    public String toString() {
        return ("(" + super.getLeft().toString() + " + " + super.getRight().toString() + ")");
    }

}
