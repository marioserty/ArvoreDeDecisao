/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

import Data.Data;

/**
 *
 * @author Mário
 */
public class LessThan extends Binary {

    private int variable;
    private double label;

    public LessThan(int var, double label, ArithmeticExpression r, ArithmeticExpression l) {
        this.variable = var;
        this.label = label;
        super.right = r;
        super.left = l;
    }

    @Override
    public double processOnTrain(int instance) {
        if (Data.train[instance][getVariable()] < getLabel()) {
            return super.left.processOnTrain(instance);
        } else {
            return super.right.processOnTrain(instance);
        }
    }

    @Override
    public double processOnTest(int instance) {
        if (Data.train[instance][getVariable()] < getLabel()) {
            return super.left.processOnTest(instance);
        } else {
            return super.right.processOnTest(instance);
        }
    }

    public String toString(Data d) {
        return "if( data[" + variable + "] < " + label + " ){" + "\n\t" + (super.getLeft().toString()) + "\n\t" + "\n}else{" + "\n\t" + (super.getRight().toString()) + "\n\t" + "}\n";
    }

    public int getVariable() {
        return variable;
    }

    public void setVariavel(int variable) {
        this.variable = variable;
    }

    public double getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
