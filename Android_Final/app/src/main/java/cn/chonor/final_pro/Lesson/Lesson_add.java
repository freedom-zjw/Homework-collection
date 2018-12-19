package cn.chonor.final_pro.Lesson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.model.Course;
import cn.chonor.final_pro.model.Teacher;
import cn.chonor.final_pro.notice.Notice_receive;

/**
 * Created by Chonor on 2018/1/1.
 */

public class Lesson_add extends AppCompatActivity{
    private EditText name,college,pos,hour,score,info;
    private TextInputLayout cname,ccollege,cpos,chour,cscore;
    private NumberPicker week,time;
    private Button add;
    private Teacher teacher;
    private Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_add_activity);

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.add_lesson_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.lesson_add_layout).setBackgroundDrawable(bitmapDrawable);

        Init();
        Listener();
    }
    private void Listener(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    course=new Course();
                    course.setName(name.getText().toString());
                    course.setCollege(college.getText().toString());
                    course.setTid(teacher.getId());
                    course.setTname(teacher.getName());
                    course.setPos(pos.getText().toString());
                    course.setWeek(String.valueOf(week.getValue()));
                    course.setTime(String.valueOf(time.getValue()));
                    course.setHour(hour.getText().toString());
                    course.setCredit(score.getText().toString());
                    String str=info.getText().toString();
                    if(str.length()!=0)course.setInfo(str);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Coursedb coursedb=new Coursedb();
                            Message msg = Message.obtain();
                            msg.what=0;
                            try{
                                coursedb.insert(course);
                                msg.what=1;
                                coursedb.close();
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
                Toast.makeText(Lesson_add.this, "添加失败请检查网络", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(Lesson_add.this, "添加成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }
    };
    private boolean checkInput(){
        if(name.getText().length()==0){
            cname.setError("课程名称不可为空");
            return false;
        }
        if(college.getText().length()==0){
            ccollege.setError("开课单位不可为空");
            return false;
        }

        if(hour.getText().length()==0){
            chour.setError("学时不可为空");
            return false;
        }
        if(score.getText().length()==0){
            score.setError("学分不可为空");
            return false;
        }
        if(pos.getText().length()==0){
            cpos.setError("上课地点不可为空");
            return false;
        }
        return true;
    }
    private void Init(){
        teacher= SaveUser.getTeacherInfo(Lesson_add.this);
        name=(EditText)findViewById(R.id.lesson_add_name);
        college=(EditText)findViewById(R.id.lesson_add_college);
        pos=(EditText)findViewById(R.id.lesson_add_pos);
        hour=(EditText)findViewById(R.id.lesson_add_hour);
        score=(EditText)findViewById(R.id.lesson_add_score);
        info=(EditText)findViewById(R.id.lesson_add_info);
        college.setText(teacher.getCollege());
        cname=(TextInputLayout)findViewById(R.id.c_add_name);
        ccollege=(TextInputLayout)findViewById(R.id.c_add_college);
        cpos=(TextInputLayout)findViewById(R.id.c_add_pos);
        chour=(TextInputLayout)findViewById(R.id.c_add_hour);
        cscore=(TextInputLayout)findViewById(R.id.c_add_score);
        cname.setErrorEnabled(true);
        ccollege.setErrorEnabled(true);
        cpos.setErrorEnabled(true);
        chour.setErrorEnabled(true);
        cscore.setErrorEnabled(true);
        week=(NumberPicker)findViewById(R.id.lesson_add_week);
        time=(NumberPicker)findViewById(R.id.lesson_add_time);
        add=(Button)findViewById(R.id.lesson_add);
        week.setMaxValue(7);
        week.setMinValue(1);
        time.setMinValue(1);
        time.setMaxValue(5);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
