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
    public double process(Data d, int instance) {
        if (d.data[instance][getVariavel()] > getLabel()) {
            return super.left.process(d, instance);
        } else {
            return super.right.process(d, instance);
        }
    }

    public String toString(Data d) {
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
