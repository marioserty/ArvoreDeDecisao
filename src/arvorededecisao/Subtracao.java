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
public class Subtracao extends Binaria{
    
    public Subtracao(ExpressaoAritmetica esq, ExpressaoAritmetica dir){
        super.direita = dir;
        super.esquerda = esq;
    }
    
    private Subtracao(){}

    @Override
    public double processa(int instancia) {
        return (super.getEsquerda().processa(instancia) - super.getDireita().processa(instancia));
    }

    @Override
    public String toString() {
        return ( "(" + super.getEsquerda().toString() + " - " +super.getDireita().toString() + ")");
    }
}
