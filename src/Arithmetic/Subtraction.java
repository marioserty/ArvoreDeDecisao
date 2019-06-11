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
public class Subtraction extends Binary {

    public Subtraction(ArithmeticExpression esq, ArithmeticExpression dir) {
        super.direita = dir;
        super.esquerda = esq;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processa(int instancia) {
        return (super.getEsquerda().processa(instancia) - super.getDireita().processa(instancia));
    }

    @Override
    public String toString() {
        return ("(" + super.getEsquerda().toString() + " - " + super.getDireita().toString() + ")");
    }
}
