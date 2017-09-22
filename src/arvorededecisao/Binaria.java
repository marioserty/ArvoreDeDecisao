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
public abstract class Binaria implements ExpressaoAritmetica{
    ExpressaoAritmetica esquerda;
    ExpressaoAritmetica direita;
    
    @Override
    public Object clone(){
        Object o = new Object();
        return o;
    }

    public ExpressaoAritmetica getEsquerda() {
        return esquerda;
    }

    public ExpressaoAritmetica getDireita() {
        return direita;
    }
    
}
