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
public final class Constant extends Unary{
    
    double value;
    
    public Constant(double v){
        this.value = v;
    }
    
    @Override
    public Object clone(){
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double process(int instancia) {
        return this.value;
    }

    public String toString(Data d) {
        return Double.toString(value);
    }
    
    public void setValor(double newV){
        this.value = newV;
    }    

    @Override
    public ArithmeticExpression getRight() {
        return new Constant(value);
    }

    @Override
    public ArithmeticExpression getLeft() {
        return new Constant(value);
    }
}
