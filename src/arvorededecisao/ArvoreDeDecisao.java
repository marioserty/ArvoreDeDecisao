/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorededecisao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import leitura.Dados;

/**
 *
 * @author Mário
 */
public class ArvoreDeDecisao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        FileReader file = new FileReader("train2_5.csv");
        FileReader file2 = new FileReader("train2_5Labels.csv");
        FileReader file3 = new FileReader("test2_5.csv");        
        
        Dados dados = new Dados();
        dados.setEntrada(file);
        dados.setSaidaDesejada(file2);
        
        GeradorDeArvore g = new GeradorDeArvore();
        ExpressaoAritmetica e = g.geraAlturaTres();
        ExpressaoAritmetica e2 = e;
        
        for (int i = 0; i < 100_000; i++) {
            
            e2 = g.mutacao(e2);
            
            if (erroFuncao(e2) < erroFuncao(e)) {
                e = e2;
            }else{
                e2 = e;
            }
                
            
        }
        System.out.println(e);
        System.out.println("Erro da função: " + erroFuncao(e));
        
        dados.setEntrada(file3);
        
        double[] vet = new double[275];
        for (int i = 0; i < 275; i++) {
            vet[i] = sigm(e, i);
        }
        dados.setSaidaSubmissao(vet);
        
    }
    
    public static double sigm(ExpressaoAritmetica exp, int instancia){
        return 1.0/(1.0 + Math.pow(Math.E, - exp.processa(instancia)));        
    }
    
    public static double erro(ExpressaoAritmetica exp, int instancia){
        return Math.pow(Dados.saidaDesejada[instancia][1] - sigm(exp, instancia), 2);
    }
    
    public static double erroFuncao(ExpressaoAritmetica exp){
        double e = 0.0;
        
        for (int i = 0; i < 275; i++) {
            e = e + erro(exp, i);
            //System.out.println(e);
        }
        
        return e;
    }
}
