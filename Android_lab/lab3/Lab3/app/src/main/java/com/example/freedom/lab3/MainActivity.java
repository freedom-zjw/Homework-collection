package com.example.freedom.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView Itemlist;
    private ListView Shopcarlist;
    private LinearLayout Shopcar;
    private List<Items> items;
    private List<Items> shopcaritems;
    private ItemslistAdapter itemslistAdapter;
    private ShopcarAdapter shopcarAdapter;
    private ImageButton switchbutton;
    final String[] Name = new String[]{"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
    final String[] Price = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.00", "¥ 132.59", "¥ 141.43", "139.43", "28.90"};
    final String[] Info = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //主界面的两个界面，Itemlist呈现商品，Shopcarlist 呈现购物车
        Itemlist = (RecyclerView) findViewById(R.id.itemslist);
        Itemlist.setLayoutManager(new LinearLayoutManager(this));
        Shopcar = (LinearLayout) findViewById(R.id.shopcarlist);
        Shopcar.setVisibility(View.INVISIBLE);//默认不显示

        //初始化商品列表
        items =  new ArrayList<Items>();
        for (int i = 0; i < Name.length; i++)
            items.add(new Items(Name[i], Info[i], Price[i]));
        itemslistAdapter = new ItemslistAdapter(items, MainActivity.this);
        Itemlist.setAdapter(itemslistAdapter);
        itemslistAdapter.setOnItemClickListener(new ItemslistAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, ItemsDetails.class);
                //使用Intent传递需要用到的值
                intent.putExtra("Name", items.get(position).getName());
                intent.putExtra("Price", items.get(position).getPrice());
                intent.putExtra("Info", items.get(position).getInfo());
                startActivityForResult(intent,1);
                //使用startActivityForResult（Intent intent . intrequestcode）方法打开新的activity,
                // 我们需要为该方法传递一个请求码。请求码的值是根据业务需要由自已设定
            }
            @Override
            public void onLongClick(int position) {
                items.remove(position);
                Toast.makeText(getApplicationContext(),"移除第"+ String.valueOf(position+1 ) + "个商品", Toast.LENGTH_SHORT).show();
                itemslistAdapter.notifyDataSetChanged();
            }
        });

        //初始化购物车列表
        shopcaritems = new ArrayList<Items>();
        shopcarAdapter = new ShopcarAdapter(shopcaritems, MainActivity.this);
        Shopcarlist=(ListView) findViewById(R.id.shopcarlist_item);
        Shopcarlist.setAdapter(shopcarAdapter);
        Shopcarlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //i值这一项在列表中的位置,l指的是这一项的id，在ArrayAdapter和SimpleAdapter中,i和l是
                //相等的，在CursorAdapter，l指的是从数据库中取出的数据在数据库中的id值
                Intent intent = new Intent(MainActivity.this, ItemsDetails.class);
                intent.putExtra("Name", shopcaritems.get(i).getName());
                intent.putExtra("Price", shopcaritems.get(i).getPrice());
                intent.putExtra("Info", shopcaritems.get(i).getInfo());
                startActivityForResult(intent,1);
            }
        });
        Shopcarlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, final int i,long l){
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品").setMessage("从购物车移除" + shopcaritems.get(i).getName() + "?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        final String removename = new String(shopcaritems.get(i).getName());
                        if (shopcaritems.remove(i)!=null ){
                            shopcarAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "已删除"+ removename, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int j) {
                        Toast.makeText(getApplicationContext(), "你选择了[取消]", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                //返回true,响应长按事件，返回false 长按短按事件都响应
                return true;
            }

        });

        //切换商品列表和购物车列表
        switchbutton = (ImageButton) findViewById(R.id.shopcar);
        switchbutton.setTag("0");
        switchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchbutton.getTag() == "0") {
                    switchbutton.setImageResource(R.drawable.mainpage);
                    switchbutton.setTag("1");
                    Shopcar.setVisibility(View.VISIBLE);
                    Itemlist.setVisibility(View.INVISIBLE);
                } else {
                    switchbutton.setImageResource(R.drawable.shoplist);
                    switchbutton.setTag("0");
                    Shopcar.setVisibility(View.INVISIBLE);
                    Itemlist.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //根据请求码做出相应的业务处理
        if(requestCode==1&&resultCode==1) {
            Bundle bud = intent.getExtras();
            String name = bud.getString("name");
            String price = bud.getString("price");
            String info = bud.getString("info");
            int cnt = bud.getInt("addshopcar");
            if (cnt > 0) {
                for (int i = 0; i < cnt; i++)
                    shopcaritems.add(new Items(name, info, price));
                shopcarAdapter.notifyDataSetChanged();
            }
        }
    }

}
