/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

import Data.Data;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mário
 */
public abstract class Unary implements ArithmeticExpression {

    ArithmeticExpression exp;

    @Override
    public Object clone() {
        try {
            return (ArithmeticExpression) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Unary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public int height(){
        return 1;
    }

    @Override
    public double processOnTrain(int instance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double processOnTest(int instance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        @Override
    public ArithmeticExpression getLeft() {
        return this;
    }

    @Override
    public ArithmeticExpression getRight() {
        return this;
    }
    
    @Override
    public void setLeft(ArithmeticExpression exp) {
        this.exp = exp;
    }

    @Override
    public void setRight(ArithmeticExpression exp) {
        this.exp = exp;
    }
}
