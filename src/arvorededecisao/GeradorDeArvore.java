/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorededecisao;

import java.util.Random;
import Reader.Data;

/**
 *
 * @author Mário
 */
public class GeradorDeArvore {
    
    public ExpressaoAritmetica geraAlturaUm(){
        if (Math.random() < 0.5) {
            return new Constante(Math.random() * 1000);
        }else{
            return new Variavel((int) (Math.random() * Data.trainNumCols - 1));
        }
    }
    
    public ExpressaoAritmetica geraAlturaDois(){
        ExpressaoAritmetica direita = geraAlturaUm();
        ExpressaoAritmetica esquerda = geraAlturaUm();
        
        if (Math.random() < 1.0/3.0) {
            return new Soma(esquerda, direita);
        }else if (Math.random() < 2.0/3.0) {
            return new Subtracao(esquerda, direita);
        }else{
            return new Multiplicacao(esquerda, direita);
        }
        
    }
    
    public ExpressaoAritmetica geraAlturaTres(){
        ExpressaoAritmetica direita;
        ExpressaoAritmetica esquerda;
        
        if (Math.random() < 1.0/3.0) {
            direita = geraAlturaDois();
            esquerda = geraAlturaUm();
        }else if (Math.random() < 2.0/3.0){
            direita = geraAlturaUm();
            esquerda = geraAlturaDois();
        }else{
            direita = geraAlturaDois();
            esquerda = geraAlturaDois();
        }
        
        if (Math.random() < 1.0/3.0) {
            return new Soma(esquerda, direita);
        }else if (Math.random() < 2.0/3.0) {
            return new Subtracao(esquerda, direita);
        }else{
            return new Multiplicacao(esquerda, direita);
        }
                
    }
    
    public ExpressaoAritmetica mutacao(ExpressaoAritmetica exp){
        /**apagar esquerda e gerar alt3, 2, apagar direita e gerar alt3, 2
        *trocar meio;
        */
        
        double d = Math.random();
        
        if (d < 1.0/4.0) {
            double d2 = Math.random();
            if (d2 < 1.0/3.0) {
                return new Soma(mutacao(geraAlturaTres()), exp);
            }else if (d2 < 2.0/3.0) {
                return new Subtracao(mutacao(geraAlturaTres()), exp);
            }else{
                return new Multiplicacao(mutacao(geraAlturaTres()), exp);
            }
        }else if (d < 2.0/4.0) {
            double d2 = Math.random();
            if (d2 < 1.0/3.0) {
                return new Soma(exp, mutacao(geraAlturaTres()));
            }else if (d2 < 2.0/3.0) {
                return new Subtracao(exp, mutacao(geraAlturaTres()));
            }else{
                return new Multiplicacao(exp, mutacao(geraAlturaTres()));
            }
        }else if (d < 3.0/4.0){
            double d2 = Math.random();
            if (d2 < 1.0/3.0) {
                return new Soma(exp.getEsquerda(), geraAlturaDois());
            }else if (d2 < 2.0/3.0) {
                return new Subtracao(exp.getEsquerda(), geraAlturaDois());
            }else{
                return new Multiplicacao(exp.getEsquerda(), geraAlturaDois());
            }
        }else{
            double d2 = Math.random();
            if (d2 < 1.0/3.0) {
                return new Soma(geraAlturaDois(), exp.getDireita());
            }else if (d2 < 2.0/3.0) {
                return new Subtracao(geraAlturaDois(), exp.getDireita());
            }else{
                return new Multiplicacao(geraAlturaDois(), exp.getDireita());
            }
        }            
        
    }
    
    public ExpressaoAritmetica geraITE_AlturaUm(){
        int variavel = (int) (Math.random() * Data.trainNumCols - 1);
        int label;
        
        if (Math.random() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, geraAlturaTres(), geraAlturaTres());
    }
    
    public ExpressaoAritmetica geraITE_AlturaDois(){
        ExpressaoAritmetica esq = geraITE_AlturaUm();
        ExpressaoAritmetica dir = geraITE_AlturaUm();
        
        int variavel = (int) (Math.random() * Data.trainNumCols - 1);
        int label;
        
        if (Math.random() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, esq, dir);
    }
    
    public ExpressaoAritmetica geraITE_AlturaTres(){
        ExpressaoAritmetica esq = geraITE_AlturaDois();
        ExpressaoAritmetica dir = geraITE_AlturaDois();
        
        int variavel = (int) (Math.random() * Data.trainNumCols - 1);
        int label;
        
        if (Math.random() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, esq, dir);
    }
    
    public ExpressaoAritmetica geraITE_AlturaQuatro(){
        ExpressaoAritmetica esq = geraITE_AlturaTres();
        ExpressaoAritmetica dir = geraITE_AlturaTres();
        
        int variavel = (int) (Math.random() * Data.trainNumCols - 1);
        int label;
        
        if (Math.random() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, esq, dir);
    }
    
    public ExpressaoAritmetica mutacaoIf(ExpressaoAritmetica ite){
        Random r = new Random();
        
        int n = r.nextInt(3);
        
        if (ite instanceof IfThenElse) {
            switch(n){
                case 0:
                    int x = r.nextInt(Data.trainNumCols - 1);
                    ((IfThenElse)ite).setLabel(x);
                    break;
                case 1:
                    this.mutacaoIf(((IfThenElse)ite).getEsquerda());
                    break;
                default:
                    this.mutacaoIf(((IfThenElse)ite).getDireita());
                    break;
                }
            return ite;
        }else{
            mutacao(ite);
        }
        
        
        return ite;
        
    }
    
    
    
}


