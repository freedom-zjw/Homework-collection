package cn.chonor.final_pro.Lesson;

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
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import cn.chonor.final_pro.DataBase.Choicedb;
import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.homework.Homework_receive;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.model.Course;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;
import cn.chonor.final_pro.notice.Notice_receive;
import cn.chonor.final_pro.notice.Notice_send;

/**
 * Created by Jy on 2017/12/24.
 */

/**
 * 课程列表
 */
public class Lesson_information extends AppCompatActivity {
    private Integer cid=-1;
    private Integer score=0;
    private Course course=null;
    private boolean logined=false;
    private boolean isTeacher=false;
    private String usernum="";
    private Student student;
    private Teacher teacher;
    private TextView coursename;
    private TextView coursetimepos;
    private TextView coursetname;
    private TextView coursecollege;
    private TextView coursehour;
    private TextView courseinfo;
    private TextView coursescore;
    private Button button;
    private Button homework;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_information_activity);
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.lessoninfo_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.lesson_infor_layout).setBackgroundDrawable(bitmapDrawable);

        Intent intent=getIntent();
        cid=intent.getIntExtra("cid",-1);
        if(cid==-1){setResult(RESULT_OK);finish();}
        GetLogin();
    }

    private void GetLogin() {
        usernum = SaveUser.getUserId(Lesson_information.this);
        if (usernum.equals("")) {
            logined = false;
            setResult(RESULT_OK);
            finish();
        } else {
            logined = true;
            if (usernum.length() == 6) {//教师
                isTeacher = true;
                teacher = SaveUser.getTeacherInfo(Lesson_information.this);
            } else {//学生
                isTeacher = false;
                student = SaveUser.getStudentInfo(Lesson_information.this);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Coursedb coursedb=new Coursedb();
                    Choicedb choicedb=new Choicedb();
                    Message msg = Message.obtain();
                    msg.what=0;
                    try{
                        course=coursedb.selectBycid(cid);
                        if (!isTeacher&&logined){
                            score=choicedb.queryscore(student.getId(),cid);
                        }
                        choicedb.close();
                        coursedb.close();
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
                Toast.makeText(Lesson_information.this, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);finish();
            }
            else if(msg.what==1){
                if(course==null){setResult(RESULT_OK);finish();}
                else{
                    init();
                }
            }

        }
    };


    private void init(){

        coursename=(TextView)findViewById(R.id.lesson_infor_name);
        coursetimepos=(TextView)findViewById(R.id.lesson_infor_info);
        coursetname=(TextView)findViewById(R.id.lesson_infor_teacher);
        coursecollege=(TextView)findViewById(R.id.lesson_infor_kaikedanwei);
        coursehour=(TextView)findViewById(R.id.lesson_hour);
        courseinfo=(TextView)findViewById(R.id.lesson_infor_introduction);
        coursescore=(TextView)findViewById(R.id.lesson_infor_score);
        button=(Button)findViewById(R.id.lesson_infor_checknotice);
        homework=(Button)findViewById(R.id.lesson_infor_hw);
        if(isTeacher){
            if(teacher.getId()==course.getTid()) {
                button.setText("发布课程通知");
                homework.setVisibility(View.VISIBLE);
            }
            else button.setVisibility(View.GONE);
        }
        else{
            button.setText("查看课程通知");
            if(score>0){
                coursescore.setVisibility(View.VISIBLE);
                coursescore.setText("得分: "+String.valueOf(score));
                homework.setVisibility(View.VISIBLE);
            }
            if(score==0){
                homework.setVisibility(View.VISIBLE);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTeacher&&teacher.getId()==course.getTid()){
                    Intent intent=new Intent(Lesson_information.this,Notice_send.class);
                    intent.putExtra("cid",cid);
                    startActivityForResult(intent,100);
                }
                else{
                    Intent intent=new Intent(Lesson_information.this,Notice_receive.class);
                    intent.putExtra("cid",cid);
                    startActivityForResult(intent,101);
                }
            }
        });
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Lesson_information.this,Homework_receive.class);
                intent.putExtra("cid",cid);
                startActivityForResult(intent,100);
            }
        });
        coursename.setText(course.getName());
        coursetimepos.setText("课程安排：周"+course.getWeek()+" 第"+course.getTime()+"节，地点："+course.getPos());
        coursetname.setText("任课老师："+course.getTname());
        coursecollege.setText("开课单位："+course.getCollege());
        coursehour.setText("学分："+course.getCredit()+"    学时："+course.getHour());
        courseinfo.setText("课程简介："+course.getInfo());



    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}