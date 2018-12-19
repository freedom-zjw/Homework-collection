package cn.chonor.final_pro.notice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.DataBase.Noticedb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.Lesson.Lesson_information;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.model.Notice;

/**
 * Created by Jy on 2017/12/25.
 */

/**
 * 通知详细信息，这里直接用了intent传过来的参数，没有需要调用数据库的
 */
public class Notice_information extends AppCompatActivity {
    private TextView notice_infor_title;
    private TextView notice_infor_date;
    private TextView notice_infor_content;
    private Integer nid;
    private Notice notice=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_information_activity);
        setTitle("通知");

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.notice_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.notice_infor_layout).setBackgroundDrawable(bitmapDrawable);

        Intent intent=getIntent();
        nid=intent.getIntExtra("nid",-1);
        if(nid==-1){setResult(RESULT_OK);finish();}
        new Thread(new Runnable() {
            @Override
            public void run() {
                Noticedb noticedb=new Noticedb();
                Message msg = Message.obtain();
                msg.what=0;
                try{
                    notice=noticedb.selectBynid(nid);
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
                Toast.makeText(Notice_information.this, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);finish();
            }
            else if(msg.what==1){
                if(notice==null){setResult(RESULT_OK);finish();}
                else{
                    Init();
                }
            }

        }
    };
    private void Init(){
        notice_infor_title=(TextView)findViewById(R.id.notice_infor_title);
        notice_infor_date=(TextView)findViewById(R.id.notice_infor_date);
        notice_infor_content=(TextView)findViewById(R.id.notice_infor_content);


        notice_infor_title.setText(notice.getCname()+": "+notice.getTitle());
        notice_infor_content.setText(notice.getInfo());
        notice_infor_date.setText("通知日期："+notice.getStarttime()+" — "+notice.getEndtime());
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
