/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arithmetic;

/**
 *
 * @author Mário
 */
public abstract class Binary implements ArithmeticExpression{
    protected ArithmeticExpression left;
    protected ArithmeticExpression right;
    
    @Override
    public Object clone(){
        try {
            return (ArithmeticExpression) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public ArithmeticExpression getLeft() {
        return left;
    }

    @Override
    public ArithmeticExpression getRight() {
        return right;
    }
    
    @Override
    public void setLeft(ArithmeticExpression exp) {
        this.left = exp;
    }

    @Override
    public void setRight(ArithmeticExpression exp) {
        this.right = exp;
    }
    
}
