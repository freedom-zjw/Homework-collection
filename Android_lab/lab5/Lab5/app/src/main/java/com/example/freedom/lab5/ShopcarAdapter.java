package com.example.freedom.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by freedom on 2017/10/28.
 */

public class ShopcarAdapter extends BaseAdapter {
    private List<Items> items;
    private Context context;
    public ShopcarAdapter( List<Items> items, Context context){
        this.items=items;
        this.context = context;
    }
    @Override
    public int getCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }
    @Override
    public Object getItem(int i) {
        if (items == null) {
            return null;
        } else {
            return items.get(i);
        }
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //新声明一个View变量和ViewHolder变量
        View convertView;
        ViewHolder holder;

        //当view为空时才加载布局，并且创建一个ViewHolder，获得布局中的3个控件
        if (view == null) {
            //通过inflate的方法加载布局，
            convertView= LayoutInflater.from(context).inflate(R.layout.shopcar, null);
            holder = new ViewHolder();
            holder.Firstletter=(TextView)convertView.findViewById(R.id.shopcar_icon);
            holder.Name = (TextView) convertView.findViewById(R.id.shopcar_name);
            holder.Price = (TextView) convertView.findViewById(R.id.shopcar_value);
            convertView.setTag(holder);//用setTag方法将处理好的viewHolder放入view中
        } else {//否则，让convertView等于view，然后从中取出ViewHolder即可
            convertView = view;
            holder = (ViewHolder) convertView.getTag();
        }
        //从viewHolder中取出对应的对象，然后赋值给它们
        holder.Firstletter.setText(items.get(i).getName().substring(0,1).toUpperCase());
        holder.Name.setText(items.get(i).getName());
        holder.Price.setText(items.get(i).getPrice());
        //将这个处理好的view返回
        return convertView;
    }
    private class ViewHolder {
        public TextView Name;
        public TextView Price;
        public TextView Firstletter;
    }
}
