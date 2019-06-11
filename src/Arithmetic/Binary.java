/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mário
 */
public abstract class Binary implements ArithmeticExpression{
    ArithmeticExpression esquerda;
    ArithmeticExpression direita;
    
    @Override
    public Object clone(){
        try {
            return (ArithmeticExpression) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Binary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArithmeticExpression getEsquerda() {
        return esquerda;
    }

    public ArithmeticExpression getDireita() {
        return direita;
    }
    
}
