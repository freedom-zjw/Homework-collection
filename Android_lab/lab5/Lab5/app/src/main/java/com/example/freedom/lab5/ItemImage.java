package com.example.freedom.lab5;

/**
 * Created by freedom on 2017/10/30.
 */

public class ItemImage {
    public ItemImage(){}
    static int getImg(String name){
        switch (name){
            case "Enchated Forest":
                return R.mipmap.enchatedforest;
            case "Arla Milk":
                return R.mipmap.arla;
            case "Devondale Milk":
                return R.mipmap.devondale;
            case "Kindle Oasis":
                return R.mipmap.kindle;
            case "waitrose 早餐麦片":
                return R.mipmap.waitrose;
            case "Mcvitie's 饼干":
                return R.mipmap.mcvitie;
            case "Ferrero Rocher":
                return R.mipmap.ferrero;
            case "Maltesers":
                return R.mipmap.maltesers;
            case "Lindt":
                return R.mipmap.lindt;
            case "Borggreve":
                return R.mipmap.borggreve;
            default:
                return R.mipmap.enchatedforest;
        }
    }
    static int getDetailImg(String name){
        switch (name){
            case "Enchated Forest":
                return R.drawable.enchatedforest;
            case "Arla Milk":
                return R.drawable.arla;
            case "Devondale Milk":
                return R.drawable.devondale;
            case "Kindle Oasis":
                return R.drawable.kindle;
            case "waitrose 早餐麦片":
                return R.drawable.waitrose;
            case "Mcvitie's 饼干":
                return R.drawable.mcvitie;
            case "Ferrero Rocher":
                return R.drawable.ferrero;
            case "Maltesers":
                return R.drawable.maltesers;
            case "Lindt":
                return R.drawable.lindt;
            case "Borggreve":
                return R.drawable.borggreve;
            default:
                return R.drawable.enchatedforest;
        }
    }
}
