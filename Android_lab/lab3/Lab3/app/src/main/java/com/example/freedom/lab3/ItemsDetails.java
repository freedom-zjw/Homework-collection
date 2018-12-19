package com.example.freedom.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freedom on 2017/10/22.
 */

public class ItemsDetails extends AppCompatActivity{
    private ListView OptionView;
    private List<String> option;
    private OptionAdapter mOptionAdapter;
    private ImageButton starbutton;
    private ImageButton shopcarbutton;
    private TextView Tname;
    private TextView Tprice;
    private TextView Tinfo;
    private ImageView img;
    private int addshopcar=0;
    private String name;
    private String price;
    private String info;
    private Intent intent;
    final String[] OptionName= new String[]{"一键下单", "分享商品","不感兴趣","查看更多商品促销信息"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetail);
        intent=getIntent();//获取传递过来的消息
        name = intent.getStringExtra("Name");
        price = intent.getStringExtra("Price");
        info = intent.getStringExtra("Info");
        //商品信息
        Tname = (TextView)findViewById(R.id.itemdetail_r1_item_name);
        Tprice = (TextView)findViewById(R.id.itemdetail_r2_price);
        Tinfo = (TextView)findViewById(R.id.itemdetail_r2_info);
        img = (ImageView)findViewById(R.id.itemdetail_r1_picture);
        Tname.setText(name);
        Tprice.setText(price);
        Tinfo.setText(info);
        switch (name){
            case "Enchated Forest":
                img.setImageResource(R.drawable.enchatedforest);
                break;
            case "Arla Milk":
                img.setImageResource(R.drawable.arla);
                break;
            case "Devondale Milk":
                img.setImageResource(R.drawable.devondale);
                break;
            case "Kindle Oasis":
                img.setImageResource(R.drawable.kindle);
                break;
            case "waitrose 早餐麦片":
                img.setImageResource(R.drawable.waitrose);
                break;
            case "Mcvitie's 饼干":
                img.setImageResource(R.drawable.mcvitie);
                break;
            case "Ferrero Rocher":
                img.setImageResource(R.drawable.ferrero);
                break;
            case "Maltesers":
                img.setImageResource(R.drawable.maltesers);
                break;
            case "Lindt":
                img.setImageResource(R.drawable.lindt);
                break;
            case "Borggreve":
                img.setImageResource(R.drawable.borggreve);
                break;
        }
        //详情页底部功能列表
        option =  new ArrayList<String>();
        for (int i = 0; i < OptionName.length; i++)
            option.add(OptionName[i]);
        mOptionAdapter = new OptionAdapter(ItemsDetails.this,option);
        OptionView = (ListView) findViewById(R.id.itemdetail_option);
        OptionView.setAdapter(mOptionAdapter);

        //星星按钮变化
        starbutton = (ImageButton) findViewById(R.id.itemdetail_r1_starbutton);
        starbutton.setTag("0");
        starbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (starbutton.getTag() == "0") {
                    starbutton.setImageResource(R.drawable.full_star);
                    starbutton.setTag("1");
                } else {
                    starbutton.setImageResource(R.drawable.empty_star);
                    starbutton.setTag("0");

                }
            }
        });

        //点击购物按钮
        shopcarbutton= (ImageButton) findViewById(R.id.itemdetail_r2_shopcar);
        shopcarbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"商品已添加到购物车",Toast.LENGTH_SHORT).show();
                addshopcar += 1;
            }
        });

        //点击返回按钮
        shopcarbutton= (ImageButton) findViewById(R.id.itemdetail_r1_backbutton);
        shopcarbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                intent.putExtra("name",name);
                intent.putExtra("price",price);
                intent.putExtra("info",info);
                intent.putExtra("addshopcar",addshopcar);
                setResult(1,intent);
                finish();
            }
        });

    }
}
