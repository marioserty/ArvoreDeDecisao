/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Arithmetic.Subtraction;
import Arithmetic.Addition;
import Arithmetic.Constant;
import Arithmetic.Multiplication;
import Arithmetic.Variable;
import Arithmetic.ArithmeticExpression;
import Arithmetic.IfThenElse;
import java.util.Random;
import Reader.Data;

/**
 *
 * @author Mário
 */
public class TreeGenerator {
    Random r;
    
    public TreeGenerator(int seed){
        r = new Random(seed);
    }
    
    public ArithmeticExpression geraAlturaUm(){
        if (r.nextDouble() < 0.5) {
            return new Constant(r.nextDouble() * 1000);
        }else{
            return new Variable((int) (r.nextDouble() * Data.trainNumCols - 1));
        }
    }
    
    public ArithmeticExpression geraAlturaDois(){
        ArithmeticExpression direita = geraAlturaUm();
        ArithmeticExpression esquerda = geraAlturaUm();
        
        if (r.nextDouble() < 1.0/3.0) {
            return new Addition(esquerda, direita);
        }else if (r.nextDouble() < 2.0/3.0) {
            return new Subtraction(esquerda, direita);
        }else{
            return new Multiplication(esquerda, direita);
        }
        
    }
    
    public ArithmeticExpression geraAlturaTres(){
        ArithmeticExpression direita;
        ArithmeticExpression esquerda;
        
        if (r.nextDouble() < 1.0/3.0) {
            direita = geraAlturaDois();
            esquerda = geraAlturaUm();
        }else if (r.nextDouble() < 2.0/3.0){
            direita = geraAlturaUm();
            esquerda = geraAlturaDois();
        }else{
            direita = geraAlturaDois();
            esquerda = geraAlturaDois();
        }
        
        if (r.nextDouble() < 1.0/3.0) {
            return new Addition(esquerda, direita);
        }else if (r.nextDouble() < 2.0/3.0) {
            return new Subtraction(esquerda, direita);
        }else{
            return new Multiplication(esquerda, direita);
        }
                
    }
    
    public ArithmeticExpression mutacao(ArithmeticExpression exp){
        /**apagar esquerda e gerar alt3, 2, apagar direita e gerar alt3, 2
        *trocar meio;
        */
        
        double d = r.nextDouble();
        
        if (d < 1.0/4.0) {
            double d2 = r.nextDouble();
            if (d2 < 1.0/3.0) {
                return new Addition(mutacao(geraAlturaTres()), exp);
            }else if (d2 < 2.0/3.0) {
                return new Subtraction(mutacao(geraAlturaTres()), exp);
            }else{
                return new Multiplication(mutacao(geraAlturaTres()), exp);
            }
        }else if (d < 2.0/4.0) {
            double d2 = r.nextDouble();
            if (d2 < 1.0/3.0) {
                return new Addition(exp, mutacao(geraAlturaTres()));
            }else if (d2 < 2.0/3.0) {
                return new Subtraction(exp, mutacao(geraAlturaTres()));
            }else{
                return new Multiplication(exp, mutacao(geraAlturaTres()));
            }
        }else if (d < 3.0/4.0){
            double d2 = r.nextDouble();
            if (d2 < 1.0/3.0) {
                return new Addition(exp.getEsquerda(), geraAlturaDois());
            }else if (d2 < 2.0/3.0) {
                return new Subtraction(exp.getEsquerda(), geraAlturaDois());
            }else{
                return new Multiplication(exp.getEsquerda(), geraAlturaDois());
            }
        }else{
            double d2 = r.nextDouble();
            if (d2 < 1.0/3.0) {
                return new Addition(geraAlturaDois(), exp.getDireita());
            }else if (d2 < 2.0/3.0) {
                return new Subtraction(geraAlturaDois(), exp.getDireita());
            }else{
                return new Multiplication(geraAlturaDois(), exp.getDireita());
            }
        }            
        
    }
    
    public ArithmeticExpression geraITE_AlturaUm(){
        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;
        
        if (r.nextDouble() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, geraAlturaTres(), geraAlturaTres());
    }
    
    public ArithmeticExpression geraITE_AlturaDois(){
        ArithmeticExpression esq = geraITE_AlturaUm();
        ArithmeticExpression dir = geraITE_AlturaUm();
        
        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;
        
        if (r.nextDouble() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, esq, dir);
    }
    
    public ArithmeticExpression geraITE_AlturaTres(){
        ArithmeticExpression esq = geraITE_AlturaDois();
        ArithmeticExpression dir = geraITE_AlturaDois();
        
        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;
        
        if (r.nextDouble() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, esq, dir);
    }
    
    public ArithmeticExpression geraITE_AlturaQuatro(){
        ArithmeticExpression esq = geraITE_AlturaTres();
        ArithmeticExpression dir = geraITE_AlturaTres();
        
        int variavel = (int) (r.nextDouble() * Data.trainNumCols - 1);
        int label;
        
        if (r.nextDouble() < 0.5) {
            label = 0;
        }else{
            label = 1;
        }
        
        return new IfThenElse(variavel, label, esq, dir);
    }
    
    public ArithmeticExpression mutacaoIf(ArithmeticExpression ite){
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


