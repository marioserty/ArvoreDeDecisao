/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorededecisao;

import java.util.Random;
import leitura.Dados;

/**
 *
 * @author Mário
 */
public class GeradorDeArvore {
    
    public ExpressaoAritmetica geraAlturaUm(){
        if (Math.random() < 0.5) {
            return new Constante(Math.random() * 1000);
        }else{
            return new Variavel((int) (Math.random() * 256));
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
        
        if (Math.random() < 1.0/3.0) {
            if (Math.random() < 1.0/3.0) {
                return new Soma(geraAlturaTres(), exp.getDireita());
            }else if (Math.random() < 2.0/3.0) {
                return new Subtracao(geraAlturaTres(), exp.getDireita());
            }else{
                return new Multiplicacao(geraAlturaTres(), exp.getDireita());
            }
        }else if (Math.random() < 2.0/3.0) {
            if (Math.random() < 1.0/3.0) {
                return new Soma(exp.getEsquerda(), geraAlturaTres());
            }else if (Math.random() < 2.0/3.0) {
                return new Subtracao(exp.getEsquerda(), geraAlturaTres());
            }else{
                return new Multiplicacao(exp.getEsquerda(), geraAlturaTres());
            }
        }else{
            if (Math.random() < 1.0/3.0) {
                return new Soma(exp.getEsquerda(), geraAlturaDois());
            }else if (Math.random() < 2.0/3.0) {
                return new Subtracao(exp.getEsquerda(), geraAlturaDois());
            }else{
                return new Multiplicacao(exp.getEsquerda(), geraAlturaDois());
            }
        }
        
    }
    
}


