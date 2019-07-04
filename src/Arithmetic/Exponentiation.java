/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

/**
 *
 * @author mario
 */
public class Exponentiation extends Binary {

    public Exponentiation(ArithmeticExpression left, ArithmeticExpression right) {
        super.right = right;
        super.left = left;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instancia) {
        return Math.pow(super.getLeft().processOnTrain(instancia), super.getRight().processOnTrain(instancia));
    }

    @Override
    public double processOnTest(int instancia) {
        return Math.pow(super.getLeft().processOnTest(instancia), super.getRight().processOnTest(instancia));
    }

    @Override
    public String toString() {
        return ("(" + super.getLeft().toString() + " ^ " + super.getRight().toString() + ")");
    }
}
