package com.example.freedom.lab4;

/**
 * Created by freedom on 2017/10/28.
 */

public class Items {
    private String name;
    private String info;
    private String price;
    public Items(String name,String info,String price){
        this.name=name;
        this.info=info;
        this.price=price;
    }
    public String getName(){return name; }
    public String getInfo(){
        return info;
    }
    public String getPrice(){
        return price;
    }
}
