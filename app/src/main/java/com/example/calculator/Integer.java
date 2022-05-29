package com.example.calculator;

public class Integer {
    private static int MAX_LIMIT=15;
    private Number number;
    private int num,pos;
    public Integer(int pos){
        number=new Number(MAX_LIMIT);
        this.pos=pos;
        updateNum();
    }
    public Integer(int a,int pos){
        number=new Number(MAX_LIMIT);
        number.updateNumber(a);
        this.pos=pos;
        updateNum();
    }
    public void updateNum(){
        num=number.getNum();
    }
    public boolean updateNumber(int a){
        boolean temp= number.updateNumber(a);
        updateNum();
        return temp;
    }
    public boolean backSpace(){
        boolean temp= number.backSpace();
        updateNum();
        return temp;
    }

    public Number getNumber() {
        return number;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
