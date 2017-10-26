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
        
        GeradorDeArvore g = new GeradorDeArvore();
        ExpressaoAritmetica e = g.geraITE_AlturaQuatro();
        for (int i = 0; i < 1_000_000; i++) {
            e = g.mutacaoIf(e);
        }
        System.out.println(e);
        System.out.println(e.processa(0));
        
//        double ensemble = 500.0;//MELHOR: 300 
//        int iteracoes = 1000;//MELHOR: 800
//        
//        Dados dados = new Dados();
//        double[] vet = new double[275];
//        
//        for (int x  = 0; x < ensemble; x++) {
//        
//            FileReader file = new FileReader("train2_5.csv");
//            FileReader file2 = new FileReader("train2_5Labels.csv");
//            FileReader file3 = new FileReader("test2_5.csv");        
//            
//            dados.setEntrada(file);
//            dados.setSaidaDesejada(file2);
//
//            GeradorDeArvore g = new GeradorDeArvore();
////            ExpressaoAritmetica e = g.geraAlturaTres();
//            ExpressaoAritmetica e = g.geraITE_AlturaQuatro();
//
////            e = g.geraAlturaTres();
////            ExpressaoAritmetica e2 = e;
//
//            ExpressaoAritmetica e2 = e;
//
//            for (int i = 0; i < iteracoes; i++) {
//
//                e2 = g.mutacaoIf(e2);
//
//                if (erroFuncao(e2) < erroFuncao(e)) {
//                    e = e2;
//                    //System.out.println("Erro da função: " + erroFuncao(e));
//                }else{
//                    e2 = e;
//                }
//            }
//
//
//            //System.out.println(e);
//            //System.out.println("Erro da função: " + erroFuncao(e));
//
//            //
//            dados.setEntrada(file3);
//
//            
//            for (int i = 0; i < 275; i++) {
//                vet[i] += sigm(e, i);
//            }
//        }
//        
//        for (int i = 0; i < 275; i++) {
//            vet[i] = vet[i]/ensemble;
//        }
//        
//        dados.setSaidaSubmissao(vet);
        
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
