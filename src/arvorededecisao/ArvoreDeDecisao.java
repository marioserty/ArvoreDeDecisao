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
        Dados train = new Dados(file);
        
        GeradorDeArvore g = new GeradorDeArvore();
        ExpressaoAritmetica e = g.geraAlturaTres();
        
        for (int i = 0; i < 1_000; i++) {
            e = g.mutacao(e);
        }
        System.out.println(e.toString());
        System.out.println(e.processa(2));
    }
    
}
