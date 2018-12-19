package cn.chonor.final_pro.notice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cn.chonor.final_pro.DataBase.Choicedb;
import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.DataBase.Noticedb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.Lesson.Lesson_list;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.model.Notice;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Jy on 2017/12/24.
 */

/**
 * 这里是通知列表
 */
public class Notice_receive extends AppCompatActivity {
    private ListView notice_list ;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String,String>> arrayList=new ArrayList<HashMap<String,String>>();
    private String map_key[]={"notice_title","notice_date","notice_content"};
    private ArrayList<Notice>notices=new ArrayList<>();
    private boolean logined=false;
    private boolean isTeacher=false;
    private String usernum="";
    private Student student;
    private Teacher teacher;
    private Integer cid=0;
    private int pos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_receive_activity);
        setTitle("我的通知");

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.notice_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.notice_receive_layout).setBackgroundDrawable(bitmapDrawable);

        GetLogin();
        Intent intent=getIntent();
        cid=intent.getIntExtra("cid",-1);
    }

    private void GetLogin(){
        usernum= SaveUser.getUserId(Notice_receive.this);
        if(usernum.equals("")){
            logined=false;
            setResult(RESULT_OK);
            finish();
        }
        else {
            logined = true;
            if(usernum.length()==6){//教师
                isTeacher=true;
                teacher=SaveUser.getTeacherInfo(Notice_receive.this);
            }else {//学生
                isTeacher=false;
                student = SaveUser.getStudentInfo(Notice_receive.this);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Noticedb noticedb=new Noticedb();
                    Message msg = Message.obtain();
                    msg.what=0;
                    try{
                        if(cid!=-1) {
                            notices=noticedb.selectBycid(cid);
                            msg.what=1;
                        }
                        else if(isTeacher) {
                            notices=noticedb.selectBytid(teacher.getId());
                            msg.what=1;
                        }else {
                            notices=noticedb.selectBysid(student.getId());
                            msg.what=1;
                        }
                    } catch (Exception e){
                    }

                    Handler.sendMessage(msg);
                }
            }).start();
        }
    }

    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(Notice_receive.this, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
            else if(msg.what==1){
                getData();
            }
            else if(msg.what==3){
                Toast.makeText(Notice_receive.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }else if(msg.what==4){
                notices.remove(pos);
                arrayList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * 从数据库里查出该用户的通知的数量以及相关信息，每次数据库改动，都要更新ListView--->noticechangexxxx
     * @return
     */
    private void getData(){


        //查询得到通知的数目以及标题和内容
        int notice_size=notices.size();

        if(notice_size==0){
            HashMap<String, String> tempHashMap = new HashMap<String, String>();
            tempHashMap.put("notice_title", "还没有通知");
            tempHashMap.put("notice_date", "");
            tempHashMap.put("notice_content","");
            arrayList.add(tempHashMap);
        }
        else{
            for(int i=0;i<notice_size;i++){
                HashMap<String, String> tempHashMap = new HashMap<String, String>();
                tempHashMap.put("notice_title",notices.get(i).getCname()+": "+notices.get(i).getTitle());
                tempHashMap.put("notice_date", notices.get(i).getStarttime());
                tempHashMap.put("notice_content", notices.get(i).getInfo());
                arrayList.add(tempHashMap);
            }
        }
        setData();
    }
    private void setData(){
        notice_list= (ListView) findViewById(R.id.notice_receive_list);
        adapter=new SimpleAdapter(this,arrayList,R.layout.listview_item,map_key,new int[]{R.id.item_notice_title,R.id.item_notice_date,R.id.item_notice_content});
        notice_list.setAdapter(adapter);

        notice_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(notices.size()>0) {
                    Intent intent = new Intent(Notice_receive.this, Notice_information.class);
                    intent.putExtra("nid", notices.get(i).getNid());
                    startActivityForResult(intent, 100);
                }
            }
        });
        if(notices.size()>0&&isTeacher){
            notice_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    //删除，数据库也做出相对应改变
                    new AlertDialog.Builder(Notice_receive.this)
                            .setTitle("确定删除该通知？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    pos=i;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Noticedb noticedb=new Noticedb();
                                            Message msg = Message.obtain();
                                            msg.what=3;
                                            try{
                                                noticedb.delete(notices.get(pos));
                                                msg.what=4;
                                            } catch (Exception e){
                                            }
                                            Handler.sendMessage(msg);
                                        }
                                    }).start();
                                }
                            })
                            .create().show();
                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}