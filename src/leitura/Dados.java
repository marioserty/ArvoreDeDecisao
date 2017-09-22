/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Mário
 */
public class Dados {
    public static double[][] entrada = new double[275][256];

    public Dados(FileReader entrada) throws IOException {
        BufferedReader buffer = new BufferedReader(entrada);
        String head = buffer.readLine();
        
        for(int i = 0; buffer.ready(); i++){
            
            String line = buffer.readLine();
            String[] vetor = line.split(",");
            
            for (int j = 0; j < vetor.length-1; j++) {
                this.entrada[i][j] = Double.valueOf(vetor[j+1]);
            }
        }
    }
    
    public void printEntrada(){
        for (int i = 0; i < 275; i++) {
            for (int j = 0; j < 256; j++) {
                System.out.print( " " + this.entrada[i][j]);
            }
            System.out.println("\n");
        }
    }
}
