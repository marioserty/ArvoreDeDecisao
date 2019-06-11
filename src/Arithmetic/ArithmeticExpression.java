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
public interface ArithmeticExpression extends Cloneable{
    
    public double processa(int instancia);
    
    @Override
    public String toString();
    
    public Object clone();
    
    public ArithmeticExpression getDireita();
    
    public ArithmeticExpression getEsquerda();
}
