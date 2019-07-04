/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

import Data.Data;

/**
 *
 * @author mario
 */
public class SquareRoot extends Binary {
    
    int column;

    public SquareRoot(int id) {
        this.column = id;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double processOnTrain(int instance) {
        return Math.sqrt(Data.train[instance][this.column]);
    }

    @Override
    public double processOnTest(int instance) {
        return Math.sqrt(Data.test[instance][this.column]);
    }

    @Override
    public String toString() {
        return ("sqrt(data[\'" + Data.columns[this.column] + "\'])");
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
