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
    public double process(int instancia) {
        return (super.getLeft().process(instancia) * super.getRight().process(instancia));
    }

    @Override
    public String toString() {
        return ("(" + super.getLeft().toString() + " * " + super.getRight().toString() + ")");
    }

}
