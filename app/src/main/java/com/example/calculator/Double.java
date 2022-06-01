package com.example.calculator;

public class Double {
    private static int INT_MAX_LIMIT=15,DECIMAL_MAX_LIMIT=10;
    private Number integer,decimal;
    private double num;
    private int pos;
    public Double(){
        integer=new Number(INT_MAX_LIMIT);
        decimal=new Number(DECIMAL_MAX_LIMIT);
        updateNum();
    }
    public Double(int pos){
        integer=new Number(INT_MAX_LIMIT);
        decimal=new Number(DECIMAL_MAX_LIMIT);
        this.pos=pos;
        updateNum();
    }
    public Double(Integer integer){
        this.integer=integer.getNumber();
        pos=integer.getPos();
        decimal=new Number(DECIMAL_MAX_LIMIT);
        updateNum();
    }
    public void updateNum(){
        num=integer.getNum()+(decimal.getNum()/Math.pow(10,decimal.getLen()));
    }
    public boolean updateDecimal(int a){
        boolean temp=decimal.updateNumber(a);
        updateNum();
        return temp;
    }
    public boolean backSpace(){
        boolean temp=decimal.backSpace();
        updateNum();
        return temp;
    }
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
