/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import CrossValidation.KFold;
import DecisionTrees.RootWiseTree;
import java.io.FileNotFoundException;
import java.io.IOException;
import Data.Data;
import DecisionTrees.LeafWiseTree;
import Metrics.AUC;
import java.util.ArrayList;

/**
 *
 * @author Mário
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        //Dataset reading
        Data.CustomRead("datasets/readmission_data_for_modeling.csv", 25000, 83, ",");
        // Tree parameters
        int seed = 1980;
        int iterations = 1_00;
        int k = 5;

        RootWiseTree rwt = new RootWiseTree(iterations, 100, 500, seed, new AUC());
        KFold kfold = new KFold(k);
        kfold.split();

        for (int i = 0; i < k; i++) {

            rwt.setValSets(kfold.getTrainIndexes()[i], kfold.getValidIndexes()[i]);
            rwt.train();
        }
    }

}
