/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorededecisao;

import leitura.Dados;

/**
 *
 * @author Mário
 */
public class Variavel extends Unaria{
    
    int coluna;
    
    public Variavel(int id){
        this.coluna = id;
    }
    
    Variavel(){}

    @Override
    public double processa(int instancia) {
        return Dados.entrada[instancia][this.coluna];
    }

    @Override
    public String toString() {
        return "x" + this.coluna;
    }
    
    public void setColuna(int newId){
        this.coluna = newId;
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
