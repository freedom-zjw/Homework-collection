package com.example.freedom.lab4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by freedom on 2017/10/28.
 */

public class ItemslistAdapter extends RecyclerView.Adapter<ItemslistAdapter.mViewHolder>{
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    private List<Items> items;
    private Context context;
    private OnItemClickListener mOnItemClickListener=null;
    //设置一个监听器
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
    //自定义ViewHolder
    class mViewHolder extends RecyclerView.ViewHolder{
        TextView itemlist_name;
        TextView itemlist_icon;
        public mViewHolder(View view){
            super(view);
            itemlist_name=(TextView)view.findViewById(R.id.itemlist_name);
            itemlist_icon=(TextView)view.findViewById(R.id.itemlist_icon);
        }
    }
    //构造函数
    public ItemslistAdapter(List<Items> items, Context context){
        this.items=items;
        this.context=context;
    }
    //创建item视图，并返回相应的vewholder
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        mViewHolder holder=new mViewHolder(view);
        return holder;
    }
    //绑定数据到正确的item视图
    @Override
    public void onBindViewHolder(final mViewHolder holder, int position){
        holder.itemlist_name.setText(items.get(position).getName());
        holder.itemlist_icon.setText(items.get(position).getName().substring(0,1).toUpperCase());
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
    //告诉Adapter列表Items的总数
    @Override
    public int getItemCount(){
        return items.size();
    }
}
