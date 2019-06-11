/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Mário
 */
public final class Constant extends Unary{
    
    double valor;
    
    public Constant(double v){
        this.valor = v;
    }
    
    public Object clone(){
        return (ArithmeticExpressions) super.clone();
    }

    @Override
    public double processa(int instancia) {
        return this.valor;
    }

    @Override
    public String toString() {
        return Double.toString(valor);
    }
    
    public void setValor(double newV){
        this.valor = newV;
    }    

    @Override
    public ArithmeticExpressions getDireita() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArithmeticExpressions getEsquerda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
