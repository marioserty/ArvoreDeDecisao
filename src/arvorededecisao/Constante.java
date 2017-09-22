/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorededecisao;

/**
 *
 * @author Mário
 */
public final class Constante extends Unaria{
    
    double valor;
    
    public Constante(double v){
        this.valor = v;
    }
    
    private Constante(){}

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
    public ExpressaoAritmetica getDireita() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExpressaoAritmetica getEsquerda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
