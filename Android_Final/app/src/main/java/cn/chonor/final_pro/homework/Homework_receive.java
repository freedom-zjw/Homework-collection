package cn.chonor.final_pro.homework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cn.chonor.final_pro.DataBase.Homeworkdb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.model.Homework;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Chonor on 2018/1/7.
 */

public class Homework_receive extends AppCompatActivity {

    private ListView homework_list ;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String,String>> arrayList=new ArrayList<HashMap<String,String>>();
    private String map_key[]={"homework_title","homework_date","homework_content"};
    private ArrayList<Homework>homeworks=new ArrayList<>();
    private boolean logined=false;
    private boolean isTeacher=false;
    private FloatingActionButton floatingActionButton;
    private String usernum="";
    private Student student;
    private Teacher teacher;
    private Integer cid=0;
    private Integer pos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_receive);
        setTitle("我的通知");

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.notice_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.homework_receive_layout).setBackgroundDrawable(bitmapDrawable);
        homework_list= (ListView) findViewById(R.id.homework_receive_list);
        Intent intent = getIntent();
        cid = intent.getIntExtra("cid", -1);
        if (cid == -1) {
            setResult(RESULT_OK);
            finish();
        }
        GetLogin();
    }

    private void GetLogin(){
        floatingActionButton=(FloatingActionButton)findViewById(R.id.homework_receive_button) ;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homework_receive.this,Homework_send.class);
                intent.putExtra("cid",cid);
                startActivityForResult(intent,101);
            }
        });
        usernum= SaveUser.getUserId(Homework_receive.this);
        if(usernum.equals("")){
            logined=false;
            setResult(RESULT_OK);
            finish();
        }
        else {
            logined = true;
            if(usernum.length()==6){//教师
                isTeacher=true;
                teacher=SaveUser.getTeacherInfo(Homework_receive.this);
                floatingActionButton.setVisibility(View.VISIBLE);
            }else {//学生
                isTeacher=false;
                student = SaveUser.getStudentInfo(Homework_receive.this);
                floatingActionButton.setVisibility(View.GONE);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Homeworkdb homeworkdb=new Homeworkdb();
                    Message msg = Message.obtain();
                    msg.what=0;
                    try{
                        homeworks=homeworkdb.selectBycid(cid);
                        msg.what=1;
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
                Toast.makeText(Homework_receive.this, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
            else if(msg.what==1){
                arrayList.clear();
                getData();
            }
            else if(msg.what==3){
                Toast.makeText(Homework_receive.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }else if(msg.what==4){
                homeworks.remove(pos);
                arrayList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * 从数据库里查出该用户的通知的数量以及相关信息，每次数据库改动，都要更新ListView--->Homeworkchangexxxx
     * @return
     */
    private void getData(){


        //查询得到通知的数目以及标题和内容
        int Homework_size=homeworks.size();

        if(Homework_size==0){
            HashMap<String, String> tempHashMap = new HashMap<String, String>();
            tempHashMap.put("homework_title", "还没作业");
            tempHashMap.put("homework_date", "");
            tempHashMap.put("homework_content","");
            arrayList.add(tempHashMap);
        }
        else{
            for(int i=0;i<Homework_size;i++){
                HashMap<String, String> tempHashMap = new HashMap<String, String>();
                tempHashMap.put("homework_title",homeworks.get(i).getTitle());
                tempHashMap.put("homework_date", homeworks.get(i).getDdl());
                tempHashMap.put("homework_content", homeworks.get(i).getInfo());
                arrayList.add(tempHashMap);
            }
        }
        setData();
    }
    private void setData(){

        adapter=new SimpleAdapter(this,arrayList,R.layout.listview_item,map_key,new int[]{R.id.item_notice_title,R.id.item_notice_date,R.id.item_notice_content});
        homework_list.setAdapter(adapter);

        homework_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(homeworks.size()>0) {
                    if(!isTeacher) {
                        Intent intent = new Intent(Homework_receive.this, Homework_infor.class);
                        intent.putExtra("hwid", homeworks.get(i).getHwid());
                        startActivityForResult(intent, 100);
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            if(requestCode==101){
               arrayList.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Homeworkdb homeworkdb=new Homeworkdb();
                        Message msg = Message.obtain();
                        msg.what=0;
                        try{
                            homeworks=homeworkdb.selectBycid(cid);
                            msg.what=1;
                        } catch (Exception e){
                        }

                        Handler.sendMessage(msg);
                    }
                }).start();
            }
        }
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
