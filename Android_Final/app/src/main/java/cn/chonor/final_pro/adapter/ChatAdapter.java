package cn.chonor.final_pro.adapter;
/**
 * Created by ASUS on 2017/12/25.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.chonor.final_pro.R;

/**
 * Created by ASUS on 2017/12/23.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.mViewHolder>{

    private Context mContext;
    private List<Map<String,String>> mdata;
    private OnItemClickListener mOnItemClickListener;
    private ImageView imageView;
    private List<ImageView> img=new ArrayList<>();

    public class Load_Internet_Img{
        Bitmap bitmap;
        ImageView imageView;

        public Load_Internet_Img(Bitmap bitmap,ImageView imageView){
            this.bitmap=bitmap;
            this.imageView=imageView;
        }
    }

    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Load_Internet_Img tmp=(Load_Internet_Img)msg.obj;
                    //imageView.setImageBitmap(bmp);
                    tmp.imageView.setImageBitmap(tmp.bitmap);
                    break;
            }
        };
    };


    public interface OnItemClickListener{
        void OnClick(View v,int position,int which);
    }
    public void setItemClickListener(OnItemClickListener listener){
        mOnItemClickListener=listener;
    }

    public ChatAdapter(Context context,List<Map<String,String>> datas){
        this.mContext=context;
        this.mdata=datas;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.chat_item,parent,false);
        mViewHolder holder=new mViewHolder(v,mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, int position) {
        holder.nickname.setText(mdata.get(position).get("nickname"));
        holder.college.setText(mdata.get(position).get("college"));
        holder.main_text.setText(mdata.get(position).get("main_text"));
        holder.up_num.setText("("+mdata.get(position).get("up_num")+")");
        holder.down_num.setText("("+mdata.get(position).get("down_num")+")");

        if(!mdata.get(position).get("chat_avatar").equals("default")){
            final String url=mdata.get(position).get("chat_avatar");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bmp = getURLimage(url);
                    Message msg = new Message();
                    msg.what = 0;
                    Load_Internet_Img load_internet_img=new Load_Internet_Img(bmp,holder.chat_avatar);
                    msg.obj = load_internet_img;
                    handle.sendMessage(msg);
                }
            }).start();
        }
        if(mdata.get(position).get("user_image").equals("gone")||mdata.get(position).get("user_image").equals("")){
            holder.user_image.setVisibility(View.GONE);
        }
        else{
            final String url=mdata.get(position).get("user_image");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bmp = getURLimage(url);
                    Message msg = new Message();
                    msg.what = 0;
                    Load_Internet_Img load_internet_img=new Load_Internet_Img(bmp,holder.user_image);
                    msg.obj = load_internet_img;
                    handle.sendMessage(msg);
                }
            }).start();
        }
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView chat_avatar;
        TextView nickname;
        TextView college;
        TextView main_text;
        ImageView user_image;
        ImageView up;
        TextView up_num;
        ImageView down;
        TextView down_num;
        TextView report;
        OnItemClickListener mListener;

        public mViewHolder(View v,OnItemClickListener tmp){
            super(v);
            chat_avatar=(ImageView) v.findViewById(R.id.chat_avatar);
            nickname=(TextView)v.findViewById(R.id.nickname);
            college=(TextView)v.findViewById(R.id.college);
            main_text=(TextView)v.findViewById(R.id.main_text);
            user_image=(ImageView) v.findViewById(R.id.user_image);
            up=(ImageView)v.findViewById(R.id.up);
            up_num=(TextView)v.findViewById(R.id.up_num);
            down=(ImageView)v.findViewById(R.id.down);
            down_num=(TextView)v.findViewById(R.id.down_num);
            report=(TextView)v.findViewById(R.id.report);
            this.mListener=tmp;
            v.setOnClickListener(this);
            up.setOnClickListener(this);
            down.setOnClickListener(this);
            report.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener!=null){
                if(v.getId()==R.id.up){
                    mListener.OnClick(v,getPosition(),0);
                }
                else if(v.getId()==R.id.down){
                    mListener.OnClick(v,getPosition(),1);
                }
                else if(v.getId()==R.id.report){
                    mListener.OnClick(v,getPosition(),2);
                }
            }
        }
    }

    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}