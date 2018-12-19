package cn.chonor.final_pro.notice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import cn.chonor.final_pro.DataBase.Noticedb;
import cn.chonor.final_pro.Date.GetTime;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.model.Notice;

/**
 * Created by Jy on 2017/12/24.
 */

public class Notice_send extends AppCompatActivity {
    private EditText notice_send_title;
    private EditText notice_send_content;
    private Button notice_send_send;
    private NumberPicker year,month,day;
    private Integer cid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_send_activity);
        setTitle("发布通知");

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.notice_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.notice_send_layout).setBackgroundDrawable(bitmapDrawable);

        Intent intent=getIntent();
        cid=intent.getIntExtra("cid",-1);
        if(cid==-1){
            setResult(RESULT_OK);
            finish();
        }
        Init();

    }
    private void Init(){
        notice_send_title=(EditText)findViewById(R.id.notice_send_title);
        notice_send_content=(EditText)findViewById(R.id.notice_send_content);
        notice_send_send=(Button)findViewById(R.id.notice_send_send);
        year=(NumberPicker)findViewById(R.id.notice_send_year);
        month=(NumberPicker)findViewById(R.id.notice_send_month);
        day=(NumberPicker)findViewById(R.id.notice_send_day);
        year.setMaxValue(Integer.valueOf(GetTime.GetYeat())+5);
        year.setMinValue(Integer.valueOf(GetTime.GetYeat()));

        month.setMinValue(1);
        month.setMaxValue(12);
        day.setMinValue(1);
        day.setMaxValue(31);
        year.setValue(Integer.valueOf(GetTime.GetYeat()));
        month.setValue(Integer.valueOf(GetTime.GetMonth()));
        day.setValue(Integer.valueOf(GetTime.GetDay()));
        notice_send_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send_title=notice_send_title.getText().toString();
                String send_date=String.valueOf(year.getValue())+"/"+String.valueOf(month.getValue())+"/"+String.valueOf(day.getValue());
                String send_content=notice_send_content.getText().toString();

                if(send_title.equals("")){
                    Toast.makeText(getApplicationContext(),"通知标题不可为空",Toast.LENGTH_SHORT).show();
                }
                else if(send_content.equals("")){
                    Toast.makeText(getApplicationContext(),"通知内容不可为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    final Notice notice=new Notice("",send_title,send_content,GetTime.GetYeat()+"/"+GetTime.GetMonth()+"/"+GetTime.GetDay(),send_date);
                    notice.setCid(cid);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Noticedb noticedb=new Noticedb();
                            Message msg = Message.obtain();
                            msg.what=0;
                            try{
                                noticedb.insert(notice);
                                msg.what=1;
                            } catch (Exception e){
                            }
                            Handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });
    }
    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(Notice_send.this, "发送失败请检查网络", Toast.LENGTH_SHORT).show();

            }
            else if(msg.what==1){
                Toast.makeText(Notice_send.this, "发送成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }
    };
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}