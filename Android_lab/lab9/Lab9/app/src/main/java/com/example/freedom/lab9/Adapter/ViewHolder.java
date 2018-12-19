package com.example.freedom.lab9.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by freedom on 2017/12/19.
 */

//自定义viewholder
public class ViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;//存储list_item的子view
    private View mConvertView;//存储list_item
    private Context mContext;

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
        mContext = context;
    }
    //获取vieHolder实例
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder holder = new ViewHolder(context, itemView, parent);
        return holder;
    }

    //vieHolder尚未将子view缓存到SparseArray数组中时，仍需要通过findViewByld（）创建View对象，如果已缓存
    //直接返回
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if(view == null)
        {
            //创建view
            view = mConvertView.findViewById(viewId);
            //将view存入mViews
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}