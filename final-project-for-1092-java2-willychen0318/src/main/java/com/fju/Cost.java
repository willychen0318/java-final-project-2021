package com.fju;

public class Cost {
    int dateline;
    int price;
    String name;
    public boolean money(int a){
        if(a<dateline){
            return true;
        }else return false;
    }
}
