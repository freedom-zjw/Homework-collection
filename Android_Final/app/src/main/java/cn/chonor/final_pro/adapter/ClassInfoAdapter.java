package cn.chonor.final_pro.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.chonor.final_pro.R;

/**
 * Created by ASUS on 2017/12/23.
 */

public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.mViewHolder>{

    private Context mContext;
    private List<Map<String,String>> mdata;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void OnClick(View v,int position);
        void OnLongClick(View v,int position);
    }
    public void setItemClickListener(OnItemClickListener listener){
        mOnItemClickListener=listener;
    }

    public ClassInfoAdapter(Context context,List<Map<String,String>> datas){
        this.mContext=context;
        this.mdata=datas;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.today_class_recycler_item,parent,false);
        mViewHolder holder=new mViewHolder(v,mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.class_name.setText(mdata.get(position).get("class_name"));
        holder.time_and_place.setText(mdata.get(position).get("time_and_place"));
        holder.teacher.setText(mdata.get(position).get("teacher"));
        if(mdata.get(position).get("img").equals("visible")){
            holder.notice.setVisibility(View.VISIBLE);
        }
        else holder.notice.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView class_name;
        TextView time_and_place;
        TextView teacher;
        ImageView notice;
        OnItemClickListener mListener;

        public mViewHolder(View v,OnItemClickListener tmp){
            super(v);
            class_name=(TextView)v.findViewById(R.id.class_name);
            time_and_place=(TextView)v.findViewById(R.id.class_time_place);
            teacher=(TextView)v.findViewById(R.id.teacher);
            notice=(ImageView)v.findViewById(R.id.notice);
            this.mListener=tmp;
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener!=null){
                mListener.OnClick(v,getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(mListener!=null){
                mListener.OnLongClick(v,getPosition());
            }
            return true;
        }
    }
}
