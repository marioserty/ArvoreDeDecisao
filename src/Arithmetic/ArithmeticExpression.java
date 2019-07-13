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
public interface ArithmeticExpression extends Cloneable{
    
    public double processOnTrain(int instance);
    
    public double processOnTest(int instance);
    
    public int height();
    
    @Override
    public String toString();
    
    public Object clone();
    
    public void setRight(ArithmeticExpression exp);
    
    public void setLeft(ArithmeticExpression exp);
    
    public ArithmeticExpression getRight();
    
    public ArithmeticExpression getLeft();
}
