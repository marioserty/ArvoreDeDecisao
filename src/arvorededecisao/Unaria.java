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
public abstract class Unaria implements ExpressaoAritmetica{
    ExpressaoAritmetica exp;
    
    @Override
    public Object clone(){
        Object o = new Object();
        return o;
    }

    public ExpressaoAritmetica getExp() {
        return exp;
    }
}
