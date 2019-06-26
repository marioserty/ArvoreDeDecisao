/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrossValidation;

import Data.Data;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class KFold implements Validation {
    
    private ArrayList[] trainIndexes;
    
    public KFold(int k){
        trainIndexes = new ArrayList[k];
    }
    
}
