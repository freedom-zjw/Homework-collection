package com.example.freedom.lab9.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.freedom.lab9.model.Github;
import com.example.freedom.lab9.model.Repos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by freedom on 2017/12/19.
 */

public abstract class CardAdapter extends RecyclerView.Adapter<ViewHolder> {
    //定义点击监听器接口
    public interface OnItemClickListener
    {
        void onClick(int position);
        boolean onLongClick(int position);
    }

    protected Context mContext; //当前上下文
    protected int mLayoutId;
    protected List<Map<String, Object>> items;//存储每一项数据
    protected LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener = null;

    //构造函数
    public CardAdapter(Context context, int layout, List<Map<String, Object>> data){
        mContext = context;
        mLayoutId = layout;
        items = data;
        mInflater = LayoutInflater.from(context);
    }

    //设置一个监听器
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    //创建视图，并返回相应viewholder
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    //绑定数据到正确的item视图
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        convert(holder, items.get(position));
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
    //告诉Adapter列表Items的总数
    @Override
    public int getItemCount(){ return items.size(); }

    public abstract void convert(ViewHolder holder, Map<String, Object> s);

    public void addData(Github github){
        HashMap<String, Object> temp = new HashMap<>();
        temp.put("login", github.getLogin());
        temp.put("id", "id："+github.getId());
        temp.put("blog", "blog:"+github.getBlog());
        items.add(temp);
        notifyDataSetChanged();
    }
    public void addData(Repos repos){
        HashMap<String, Object> temp = new HashMap<>();
        temp.put("name", repos.getName());
        temp.put("language", split(repos.getLanguage(), 20));
        temp.put("description", split(repos.getDescription(), 45));
        temp.put("html_url", repos.getHtml_url());
        items.add(temp);
        notifyDataSetChanged();
    }

    public String getData(int pos, String tag){
        return items.get(pos).get(tag).toString();
    }

    public void removeData(int pos){
        items.remove(pos);
        notifyDataSetChanged();
    }
    public void clearData(){
        items.removeAll(items);
        notifyDataSetChanged();
    }
    private String split(String s, int limit){
        String result = s;
        if(s == null){
            result = "";
        }else if(s.length() > limit){
            result = s.substring(0, limit) + "...";
        }
        return result;
    }
}

