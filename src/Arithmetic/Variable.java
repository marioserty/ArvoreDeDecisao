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
public class Variable extends Unary {

    private int column;

    public Variable(int id) {
        this.column = id;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instancia) {
        return Data.train[instancia][this.getColumn()];
    }

    @Override
    public double processOnTest(int instancia) {
        return Data.test[instancia][this.getColumn()];
    }

    @Override
    public String toString() {
        return "data[\'" + Data.columns[this.getColumn()] + "\']";
    }

    public void setColumn(int newId) {
        this.column = newId;
    }

    public int getColumn() {
        return column;
    }
}
