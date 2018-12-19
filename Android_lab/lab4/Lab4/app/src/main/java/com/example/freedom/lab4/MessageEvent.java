package com.example.freedom.lab4;

/**
 * Created by freedom on 2017/10/30.
 */

public class MessageEvent {
    private String Name;
    private String Price;
    private String Info;
    public MessageEvent(String name,String price,String info){
        this.Name=name;
        this.Price=price;
        this.Info=info;
    }
    public String getName(){
        return Name;
    }
    public String getPrice(){
        return Price;
    }
    public String getInfo(){
        return Info;
    }
}
