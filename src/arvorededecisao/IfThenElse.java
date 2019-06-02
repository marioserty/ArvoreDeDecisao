/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorededecisao;

import Reader.Data;

/**
 *
 * @author Mário
 */
public class IfThenElse extends Binaria{
    private int variavel;
    private int label;
    
    public IfThenElse(int variavel,int label, ExpressaoAritmetica dir, ExpressaoAritmetica esq){
        this.variavel = variavel;
        this.label = label;
        super.direita = dir;
        super.esquerda = esq;
    }
    
    @Override
    public double processa(int instancia) {
        if (Data.train[instancia][getVariavel()] == getLabel()) {
            return super.esquerda.processa(instancia);
        }else{
            return super.direita.processa(instancia);
        }
    }
    
    @Override
    public String toString(){
        return "if( x[" + variavel + "] == " + label + " ){"+"\n\t" +(super.getEsquerda().toString()) +"\n\t"+ "\n}else{"+"\n\t" + (super.getDireita().toString()) + "\n\t"+"}\n";
    }

    public int getVariavel() {
        return variavel;
    }

    public void setVariavel(int variavel) {
        this.variavel = variavel;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
