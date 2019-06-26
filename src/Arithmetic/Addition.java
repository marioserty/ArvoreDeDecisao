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
    public double process(Data d, int instancia) {
        return (super.getLeft().process(d, instancia) + super.getRight().process(d, instancia));
    }

    @Override
    public String toString(Data d) {
        return ("(" + super.getLeft().toString(d) + " + " + super.getRight().toString(d) + ")");
    }

}
