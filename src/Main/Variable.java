/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Reader.Data;

/**
 *
 * @author Mário
 */
public class Variable extends Unary {

    int coluna;

    public Variable(int id) {
        this.coluna = id;
    }

    @Override
    public Object clone() {
        return (ArithmeticExpressions) super.clone();
    }

    @Override
    public double processa(int instancia) {
        return Data.train[instancia][this.coluna];
    }

    @Override
    public String toString() {
        return "data[\'" + Data.columns[this.coluna] + "\']";
    }

    public void setColuna(int newId) {
        this.coluna = newId;
    }

    @Override
    public ArithmeticExpressions getDireita() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArithmeticExpressions getEsquerda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
