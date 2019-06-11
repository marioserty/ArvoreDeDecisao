/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

import Reader.Data;

/**
 *
 * @author Mário
 */
public class GreaterThan extends Binary {

    private int variable;
    private double label;

    public GreaterThan(int var, double label, ArithmeticExpression r, ArithmeticExpression l) {
        this.variable = var;
        this.label = label;
        super.right = r;
        super.left = l;
    }

    @Override
    public double process(int instance) {
        if (Data.train[instance][getVariavel()] > getLabel()) {
            return super.left.process(instance);
        } else {
            return super.right.process(instance);
        }
    }

    @Override
    public String toString() {
        return "if( data[" + variable + "] > " + label + " ){" + "\n\t" + (super.getLeft().toString()) + "\n\t" + "\n}else{" + "\n\t" + (super.getRight().toString()) + "\n\t" + "}\n";
    }

    public int getVariavel() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public double getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}