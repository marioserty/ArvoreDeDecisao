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

    int column;

    public Variable(int id) {
        this.column = id;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double process(Data d, int instancia) {
        return d.data[instancia][this.column];
    }

    @Override
    public String toString(Data d) {
        return "data[\'" + d.columns[this.column] + "\']";
    }

    public void setColumn(int newId) {
        this.column = newId;
    }

    @Override
    public ArithmeticExpression getRight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArithmeticExpression getLeft() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
