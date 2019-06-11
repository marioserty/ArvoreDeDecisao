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
public final class Constant extends Unary{
    
    double value;
    
    public Constant(double v){
        this.value = v;
    }
    
    public Object clone(){
        return (ArithmeticExpression) super.clone();
    }

    @Override
    public double process(int instancia) {
        return this.value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
    
    public void setValor(double newV){
        this.value = newV;
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
