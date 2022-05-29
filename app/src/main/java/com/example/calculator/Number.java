package com.example.calculator;

public class Number {
    private int num,len;
    private final int LIMIT;
    public Number(int LIMIT){
        num=0;
        len=0;
        this.LIMIT=LIMIT;
    }
    public boolean updateNumber(int a){
        if(num+1<=LIMIT){
            num=num*10+a;
            len++;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean backSpace(){
        if(num-1>=0){
            num/=10;
            len--;
            return true;
        }
        else{
            return false;
        }
    }

    public int getNum() {
        return num;
    }

    public int getLen() {
        return len;
    }
}
