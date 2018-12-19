package com.example.freedom.lab4;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freedom on 2017/10/28.
 */

public class ItemsDetails extends AppCompatActivity {
    private ListView OptionView;
    private List<String> option;
    private OptionAdapter mOptionAdapter;
    private ImageButton starbutton;
    private ImageButton shopcarbutton;
    private TextView Tname;
    private TextView Tprice;
    private TextView Tinfo;
    private ImageView img;
    private String name;
    private String price;
    private String info;
    private Intent intent;
    private DynamicReceiver dynamicReceiver = new DynamicReceiver();
    final String[] OptionName= new String[]{"一键下单", "分享商品","不感兴趣","查看更多商品促销信息"};
    final String DYNAMICACTION = "com.example.freedom.MyDynamicFliter";

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
        img.setImageResource(ItemImage.getDetailImg(name));

       //动态广播注册
        IntentFilter  dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(DYNAMICACTION); //添加动态广播的Action
        registerReceiver(dynamicReceiver, dynamic_filter);//注册自定义动态广播消息

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
                Intent intentBroadcast = new Intent(DYNAMICACTION);
                intentBroadcast.putExtra("Name", name);
                intentBroadcast.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                sendBroadcast(intentBroadcast);
                Toast.makeText(getApplicationContext(),"商品已添加到购物车",Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MessageEvent(name,price,info));
            }
        });

        //点击返回按钮
        shopcarbutton= (ImageButton) findViewById(R.id.itemdetail_r1_backbutton);
        shopcarbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //intent.putExtra("name",name);
                //intent.putExtra("price",price);
                //intent.putExtra("info",info);
                finish();
            }
        });
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        //广播的注销
        unregisterReceiver(dynamicReceiver);
    }

}
