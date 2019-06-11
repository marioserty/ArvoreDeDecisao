/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mário
 */
public abstract class Binary implements ArithmeticExpressions{
    ArithmeticExpressions esquerda;
    ArithmeticExpressions direita;
    
    @Override
    public Object clone(){
        try {
            return (ArithmeticExpressions) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Binary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArithmeticExpressions getEsquerda() {
        return esquerda;
    }

    public ArithmeticExpressions getDireita() {
        return direita;
    }
    
}
