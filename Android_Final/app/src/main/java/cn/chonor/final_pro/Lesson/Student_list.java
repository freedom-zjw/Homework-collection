package cn.chonor.final_pro.Lesson;

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
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cn.chonor.final_pro.DataBase.Choicedb;
import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.DataBase.Homeworkdb;
import cn.chonor.final_pro.DataBase.Studentdb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.Main_Tabhost;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.model.Course;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Chonor on 2018/1/8.
 */

public class Student_list extends AppCompatActivity {
    private ListView lesson_list;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String,String>> arrayList=new ArrayList<HashMap<String,String>>();
    private String map_key[]={"student_name","student_num","student_score"};
    private ArrayList<Student>allstudent=new ArrayList<>();
    int sid=-1;
    int tid=-1;
    private boolean logined=false;
    private boolean isTeacher=false;
    private String usernum="";
    private Student student;
    private Teacher teacher;
    private int pos=-1,score=-1;
    private int cid=-1;
    private int hwid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.lessoninfo_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.student_list).setBackgroundDrawable(bitmapDrawable);

        Intent intent=getIntent();
        cid=intent.getIntExtra("cid",-1);
        hwid=intent.getIntExtra("hwid",-1);
        setTitle("学生列表");
        GetLogin();
    }

    private void GetLogin(){
        usernum= SaveUser.getUserId(Student_list.this);
        if(usernum.equals("")){
            logined=false;
            setResult(RESULT_OK);
            finish();
        }
        else {
            logined = true;
            if(usernum.length()==6){//教师
                isTeacher=true;
                teacher=SaveUser.getTeacherInfo(Student_list.this);
            }else {//学生
                setResult(RESULT_OK);
                finish();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Studentdb studentdb=new Studentdb();
                    Message msg = Message.obtain();
                    msg.what=0;
                    try{
                        if(cid!=-1){
                            allstudent=studentdb.selectByCid(cid);
                        }

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
                Toast.makeText(Student_list.this, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
            else if(msg.what==1){
                getData();
            }
            else if(msg.what==3){
                Toast.makeText(Student_list.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }else if(msg.what==4){
                arrayList.remove(pos);
                HashMap<String, String> tempHashMap = new HashMap<String, String>();
                tempHashMap.put("student_name", "姓名："+allstudent.get(pos).getName());
                tempHashMap.put("student_num", "学号："+ allstudent.get(pos).getNum());
                tempHashMap.put("student_score","得分："+score);
                arrayList.add(pos,tempHashMap);
                adapter.notifyDataSetChanged();
            }
        }
    };
    /**
     * 从数据库里查出该用户的课程的数量以及相关信息，每次数据库改动，都要更新ListView
     * @return
     */
    private void getData(){

        //查询得到通知的数目以及标题和内容
        int lesson_size=allstudent.size();

        if(lesson_size==0){
            HashMap<String, String> tempHashMap = new HashMap<String, String>();
            tempHashMap.put("student_name", "还没有学生");
            tempHashMap.put("student_num", "");
            tempHashMap.put("student_score","");
            arrayList.add(tempHashMap);
        }
        else{
            for(int i=0;i<lesson_size;i++){
                HashMap<String, String> tempHashMap = new HashMap<String, String>();
                tempHashMap.put("student_name", "姓名："+allstudent.get(i).getName());
                tempHashMap.put("student_num", "学号："+ allstudent.get(i).getNum());
                tempHashMap.put("student_score","得分："+allstudent.get(i).getScore());
                arrayList.add(tempHashMap);

            }
        }
        setData();
    }

    private  void setData(){
        lesson_list=(ListView)findViewById(R.id.student_list);
        adapter=new SimpleAdapter(this,arrayList,R.layout.lesson_list_item,map_key,new int[]{R.id.item_lesson_name,R.id.item_lesson_info,R.id.item_lesson_teacher});
        lesson_list.setAdapter(adapter);

        lesson_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(allstudent.size()>0) {
                    final EditText input_text=new EditText(Student_list.this);
                    input_text.setInputType(InputType.TYPE_CLASS_NUMBER);
                    AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(Student_list.this);
                    alertDialogBuilder.setTitle("输入成绩：")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(input_text)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    String Input_text=input_text.getText().toString();
                                    if(Input_text.equals("")){//不可为空
                                        Toast.makeText(Student_list.this,"成绩不可为空,修改失败",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        score=Integer.valueOf(Input_text);
                                        if(score==0)  Toast.makeText(Student_list.this,"成绩非法",Toast.LENGTH_LONG).show();
                                        else{
                                            if(score>100)score=100;
                                            pos=i;

                                            UPDateScore();
                                        }
                                    }

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .setCancelable(true)
                            .create().show();
                }
            }
        });
    }
    public void UPDateScore(){
        if(cid!=-1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Choicedb choicedb=new Choicedb();
                    Message msg = Message.obtain();
                    msg.what=3;
                    try{
                        choicedb.update(cid,allstudent.get(pos).getId(),score);
                        choicedb.close();
                        msg.what=4;
                    } catch (Exception e){
                    }
                    Handler.sendMessage(msg);
                }
            }).start();
        }
        else if(hwid!=-1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Homeworkdb homeworkdb =new Homeworkdb();
                    Message msg = Message.obtain();
                    msg.what=3;
                    try{
                        homeworkdb.insertScore(hwid,sid,score);
                        homeworkdb.close();
                        msg.what=4;
                    } catch (Exception e){
                    }
                    Handler.sendMessage(msg);
                }
            }).start();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
