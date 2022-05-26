package com.example.calculator;

public class Integer {
    private static int MAX_LIMIT=15;
    private Number number;
    private int num;
    public Integer(){
        number=new Number(MAX_LIMIT);
        updateNum();
    }
    public void updateNum(){
        num=number.getNum();
    }
    public boolean updateNumber(int a){
        return number.updateNumber(a);
    }
    public boolean backSpace(){
        return number.backSpace();
    }
}
