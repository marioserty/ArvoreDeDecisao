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
public interface ExpressaoAritmetica extends Cloneable{
    
    public double processa(int instancia);
    
    @Override
    public String toString();
    
    public Object clone();
    
    public ExpressaoAritmetica getDireita();
    
    public ExpressaoAritmetica getEsquerda();
}
