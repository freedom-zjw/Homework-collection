package cn.chonor.final_pro.homework;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import cn.chonor.final_pro.DataBase.Homeworkdb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.model.Homework;


/**
 * Created by Chonor on 2018/1/7.
 */

public class Homework_infor extends AppCompatActivity  {

    private TextView homework_infor_title;
    private TextView homework_infor_date;
    private TextView homework_infor_content;
    private Integer  hwid;
    private Homework homework;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_infor);
        setTitle("作业");

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.notice_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.homework_infor_layout).setBackgroundDrawable(bitmapDrawable);

        Intent intent=getIntent();
        hwid=intent.getIntExtra("hwid",-1);
        if(hwid==-1){setResult(RESULT_OK);finish();}
        new Thread(new Runnable() {
            @Override
            public void run() {
                Homeworkdb homeworkdb=new Homeworkdb();
                Message msg = Message.obtain();
                msg.what=0;
                try{
                    homework=homeworkdb.selectByhwid(hwid);
                    msg.what=1;
                } catch (Exception e){
                }
                Handler.sendMessage(msg);
            }
        }).start();

    }
    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(Homework_infor.this, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);finish();
            }
            else if(msg.what==1){
                if(homework==null){setResult(RESULT_OK);finish();}
                else{
                    Init();
                }
            }

        }
    };
    private void Init(){
        homework_infor_title=(TextView)findViewById(R.id.homework_infor_title);
        homework_infor_date=(TextView)findViewById(R.id.homework_infor_date);
        homework_infor_content=(TextView)findViewById(R.id.homework_infor_content);


        homework_infor_title.setText(homework.getTitle());
        homework_infor_content.setText(homework.getInfo());
        homework_infor_date.setText("截止日期："+homework.getDdl());
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
